package ru.it.sd.service;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.*;
import ru.it.sd.exception.ServiceException;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.*;
import ru.it.sd.util.EntityUtils;
import ru.it.sd.util.ResourceMessages;

import java.util.*;

@Service
public class AccessService {

    private static final Logger LOG = LoggerFactory.getLogger(AccessService.class);

    private AttributeAccessDao attributeAccessDao;
    private GrantDao grantDao;
    private SecurityService securityService;
    private CodeDao codeDao;
    private WorkgroupDao workgroupDao;

    public AccessService(AttributeAccessDao attributeAccessDao, GrantDao grantDao, SecurityService securityService, WorkgroupDao workgroupDao, CodeDao codeDao){
        this.attributeAccessDao = attributeAccessDao;
        this.grantDao = grantDao;
        this.securityService = securityService;
        this.workgroupDao = workgroupDao;
        this.codeDao = codeDao;
    }

    /**
     * Функция получения прав доступа на все атрибуты сущности по правам доступа {@link Grant}
     * @param grant права доступа
     * @return возращает мапу со списком атрибутов и их доступности {@link AttributeGrantRule}
     */
    private Map<String, AttributeGrantRule> getAttributeAccess(Grant grant){
        //todo оптимизировать
        Map<String, AttributeGrantRule> attributeEntitlement = new HashMap<>();
        Class clazz = EntityUtils.getEntityClass(grant.getEntityType().getAlias());
        Map<String, FieldMetaData> fieldMetaDataMap = MetaUtils.getFieldsMetaData(clazz);
        for(FieldMetaData fmd: fieldMetaDataMap.values()){
            //Если записан id атрибута в моделе
            if(!Objects.equals(fmd.getAttribute(),Long.MIN_VALUE)){
                //Если нет доступа на чтение, то записываем 0(спрятать)
                if(grant.getRead() != GrantRule.NONE) {

                    Map<String, String> filter = new HashMap<>();
                    filter.put("grantId",String.valueOf(grant.getId()));
                    filter.put("attributeId", String.valueOf(fmd.getAttribute()));
                    List<AttributeAccess> attributeAccessList = attributeAccessDao.list(filter);
                    AttributeAccess attributeAccess;
                    if(attributeAccessList.size() > 1) {
                        throw new ServiceException(ResourceMessages.getMessage("error.too.many.result"));
                    }else if(attributeAccessList.isEmpty()){
                        attributeAccess = null;
                    }else {
                        attributeAccess = attributeAccessList.get(0);
                    }
                    //Проверка прав для атрибутов

                    if(attributeAccess == null && grant.getUpdate() != GrantRule.NONE){//Если есть права доступа на редактирование и редактирование атрибута
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.UPDATE);
                    } else if(attributeAccess == null){//Если есть права доступа на редактирование атрибута, но нет на редактирование сущности
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.READ);
                    } else if(!attributeAccess.getModify()){//Если есть права доступа на чтение атрибутов
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.READ);
                    } else{
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.HIDE);
                    }
                } else {
                    attributeEntitlement.put(fmd.getName(), AttributeGrantRule.HIDE);
                }

            }
        }
        return attributeEntitlement;
    }

    /**
     * Проверка всех прав доступа для конкретной сущности и текущего пользователя и объединение их
     * @param entity сущность для проверки прав доступа
     * @return {@link Grant} общие права доступа к конкретной сущности
     */
    public Pair<Grant, Map<String, AttributeGrantRule>> getEntityAccess(HasFolder entity){
        User user = securityService.getCurrentUser();
        Long accountId = user.getId();
        Map<String, String> filter = new HashMap<>();
        filter.put("accountId", accountId.toString());
        filter.put("entityId", EntityType.getByClass(entity.getClass()).getId().toString());
        List<Grant> grantList = grantDao.list(filter);
        //Результат проверки прав доступа
        Grant entityAccess = new Grant();
        //Значение по умолчанию
        entityAccess.setCreate(GrantRule.NONE);
        entityAccess.setRead(GrantRule.NONE);
        entityAccess.setUpdate(GrantRule.NONE);
        entityAccess.setDelete(GrantRule.NONE);
        entityAccess.setHistoryCreate(GrantRule.NONE);
        entityAccess.setHistoryRead(GrantRule.NONE);
        entityAccess.setHistoryUpdate(GrantRule.NONE);
        entityAccess.setHistoryDelete(GrantRule.NONE);
        filter.put("codeId", entity.getFolder().getId().toString());
        List<BaseCode> parentFolders = codeDao.list(filter);
        //Результат проверки прав доступа атрибутов
        Map<String, AttributeGrantRule> attributeAccessMap = new HashMap<>();
        //Цикл по всем правам доступа к сущности(ena)
        for(Grant grant: grantList){
            //Необходимость проверки условий
            Boolean folder = true;
            //Проверка папки
            if(grant.getFolder() != null){
                //Если папка сущности == null или папка прав достпа не является родительской, то условие папки не выполняется
                if(entity.getFolder() == null || !isParentFolder(parentFolders, grant.getFolder())){
                    folder = false;
                }
            }
            //Если условие папки для конкретной сущности выполняются, то права распостраняются на эту сущность
            if (folder){
                //Если у сущности есть статус
                if(entity instanceof HasStatus) {
                    HasStatus hasStatus = (HasStatus)entity;
                    //Входит ли статус в диапозон статусов(только для редактирования)
                    if(statusBetween(grant, hasStatus.getStatus())) {
                        setUpdateAccess(entityAccess, grant, entity, user);
                    }
                } else {//Если у сущности нет статуса, то проверяем есть ли доступ на редактирование
                    setUpdateAccess(entityAccess, grant, entity, user);
                }
                setReadAccess(entityAccess, grant, entity, user);
                if(grant.getCreate() == GrantRule.ALWAYS){
                    entityAccess.setCreate(GrantRule.ALWAYS);
                }
                if(grant.getDelete() == GrantRule.ALWAYS){
                    entityAccess.setDelete(GrantRule.ALWAYS);
                }
                //Права на историю
                //todo добавить права на чтение и редактирование истории по исполнителю и группе
                if(grant.getHistoryCreate() == GrantRule.ALWAYS){
                    entityAccess.setHistoryCreate(GrantRule.ALWAYS);
                }
                if(grant.getHistoryRead() == GrantRule.ALWAYS){
                    entityAccess.setHistoryRead(GrantRule.ALWAYS);
                }
                if(grant.getHistoryUpdate() == GrantRule.ALWAYS){
                    entityAccess.setHistoryUpdate(GrantRule.ALWAYS);
                }
                if(grant.getHistoryDelete() == GrantRule.ALWAYS){
                    entityAccess.setHistoryDelete(GrantRule.ALWAYS);
                }

                //Получение и объединение атрибутов
                if(entityAccess.getRead() != GrantRule.NONE) {
                    Map<String, AttributeGrantRule> attributeAccess = getAttributeAccess(grant);
                    for (String field : attributeAccess.keySet()) {
                        if (attributeAccessMap.get(field) == null) {
                            attributeAccessMap.put(field, attributeAccess.get(field));
                        } else if (attributeAccessMap.get(field).getId() < attributeAccess.get(field).getId()) {
                            attributeAccessMap.put(field, attributeAccess.get(field));
                        }
                    }
                }

            }
        }
        MutablePair<Grant, Map<String, AttributeGrantRule>> result = new MutablePair<>();
        result.setLeft(entityAccess);
        result.setRight(attributeAccessMap);
        return result;
    }

    /**
     * Проставление прав на чтение
     * @param entityAccess конечные права доступа сущности
     * @param grant права доступа, которые проверяем
     * @param entity сущность которую проверяем
     * @param user текущий пользователь
     */
    private void setReadAccess(Grant entityAccess, Grant grant, HasFolder entity, User user){
        switch (grant.getRead()){
            case ALWAYS:{
                entityAccess.setRead(GrantRule.ALWAYS);
            }break;
            case EXECUTOR:{
                if (entity instanceof HasAssignment){
                    HasAssignment hasAssignment = (HasAssignment) entity;
                    if (Objects.equals(user.getPerson().getId(), (hasAssignment.getExecutor().getId()))) {
                        entityAccess.setRead(GrantRule.EXECUTOR);
                    }
                }
            }break;
            case WORKGROUP:{
                if (entity instanceof HasAssignment) {
                    HasAssignment hasAssignment = (HasAssignment) entity;
                    if (isMember(hasAssignment.getWorkgroup(), user.getPerson())) {
                        entityAccess.setRead(GrantRule.WORKGROUP);
                    }
                }
            }break;
        }

    }

    /**
     * Проставление прав на редактирование
     * @param entityAccess конечные права доступа сущности
     * @param grant права доступа, которые проверяем
     * @param entity сущность которую проверяем
     * @param user текущий пользователь
     */
    private void setUpdateAccess(Grant entityAccess, Grant grant, HasFolder entity, User user){
        switch (grant.getUpdate()){
            case ALWAYS:{
                entityAccess.setUpdate(GrantRule.ALWAYS);
            }break;
            case EXECUTOR:{
                if (entity instanceof HasAssignment){
                    HasAssignment hasAssignment = (HasAssignment) entity;
                    if (Objects.equals(user.getPerson().getId(), (hasAssignment.getExecutor().getId()))) {
                        entityAccess.setUpdate(GrantRule.EXECUTOR);
                    }
                }
            }break;
            case WORKGROUP:{
                if (entity instanceof HasAssignment) {
                    HasAssignment hasAssignment = (HasAssignment) entity;
                    if (isMember(hasAssignment.getWorkgroup(), user.getPerson())) {
                        entityAccess.setUpdate(GrantRule.WORKGROUP);
                    }
                }
            }break;
        }

    }
    /**
     * Проверка находится ли status в диапозон statusFrom - statusTo
     * @param grant описание прав доступа включая диапозон статусов
     * @param status статус для проверки
     * @return true если входит в диапозон, false если нет
     */
    private Boolean statusBetween(Grant grant, EntityStatus status){
        if(Objects.isNull(grant.getStatusFrom()) || Objects.isNull(grant.getStatusTo())) return true;
        if(Objects.isNull(status.getOrder()) || Objects.isNull(grant.getStatusFrom().getOrder()) || Objects.isNull(grant.getStatusTo().getOrder())) return false;
        if((status.getOrder()>= grant.getStatusFrom().getOrder()) && (status.getOrder()<= grant.getStatusTo().getOrder())) return true;
        return false;
    }


     /** Проверка является ли folder(папка из прав доступа) родительской папкой сущности
     * @param parentCodes список родительских папок
     * @param folder папка для проверки(из прав доступа)
     * @return true если является родительской, false если нет
     */
    private Boolean isParentFolder(List<BaseCode> parentCodes, Folder folder){
        if(parentCodes.isEmpty()) return false;
        for(BaseCode baseCode: parentCodes){
            if(Objects.equals(baseCode.getId(),folder.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка является ли user членом entityWorkgroup
     * @param entityWorkgroup рабочая группа исполнителя
     * @param executor персона текущего пользователя
     * @return true если user входит, false если нет
     */
    private Boolean isMember(Workgroup entityWorkgroup, Person executor){
        Map<String, String> filter = new HashMap<>();
        filter.put("person", executor.getId().toString());
        List<Workgroup> workgroups = workgroupDao.list(filter);
        for(Workgroup workgroup: workgroups){
            if(Objects.equals(workgroup.getId(), entityWorkgroup.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка атрибута на возможноть чтения
     * @param attributeAccessList список прав доступа к атрибуту
     * @return true если можно читать, false если нужно спрятать
     */
    private Boolean isReadAvailable(List<AttributeAccess> attributeAccessList){
        if(attributeAccessList == null) return false;
        for(AttributeAccess attributeAccess: attributeAccessList){
            //Если false то можно читать
            if(!attributeAccess.getModify()) return true;
        }
        return false;
    }
}

