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
    private CodeChildsDao codeChildsDao;
    private WorkgroupDao workgroupDao;

    public AccessService(AttributeAccessDao attributeAccessDao, GrantDao grantDao, SecurityService securityService, CodeChildsDao codeChildsDao, WorkgroupDao workgroupDao){
        this.attributeAccessDao = attributeAccessDao;
        this.grantDao = grantDao;
        this.securityService = securityService;
        this.codeChildsDao = codeChildsDao;
        this.workgroupDao = workgroupDao;
    }

    /**
     * Функция получения прав доступа на все атрибуты сущности
     * @param grant права доступа
     * @param entityType тип сущности
     * @return возращает мапу со списком атрибутов и их доступности(0-спрятать, 1-читать, 2-писать)
     */
    public Map<String, AttributeGrantRule> getAttributeAccess(Grant grant, EntityType entityType){
        Map<String, AttributeGrantRule> attributeEntitlement = new HashMap<>();
        ///User user = securityService.getCurrentUser();
        Class clazz = EntityUtils.getEntityClass(entityType.getAlias());
        Map<String, FieldMetaData> fieldMetaDataMap = MetaUtils.getFieldsMetaData(clazz);

        //Получение количество прав доступа для конкретной сущности и пользователя
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

                    if(Objects.isNull(attributeAccess)){
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.UPDATE);
                    } else if(attributeAccess.getModify() == false){
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.READ);
                    } else if(attributeAccess.getModify() == true){
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
     * Проверка всех прав доступа для конкретной сущности и текущего пользователя и объединение их в однму мапу
     * @param entity сущность для проверки прав доступа
     * @return Map с ключами 'entity_read', 'entity_update', 'entity_create', 'entity_delete'
     */
    public Pair<Grant, Map<String, AttributeGrantRule>> getEntityAccess(Entity entity, EntityType entityType){
        User user = securityService.getCurrentUser();
        Long accountId = user.getId();
        Map<String, String> filter = new HashMap<>();
        filter.put("accountId", accountId.toString());
        filter.put("entityId", entityType.getId().toString());
        List<Grant> grantList = grantDao.list(filter);

        //Результат проверки прав доступа и установленные значения по умолчанию
        Grant access = new Grant();

        Map<String, Boolean> entitlement = new HashMap<>();
        entitlement.put("entity_read", false);
        entitlement.put("entity_update", false);
        entitlement.put("entity_create", false);
        entitlement.put("entity_delete", false);

        Map<String, AttributeGrantRule> attributeAccessResult = new HashMap<>();
        //Цикл по всем правам доступа к сущности(ena)
        for(Grant grant: grantList){
            //Необходимость проверки условий
            Boolean status = true;
            Boolean folder = true;
            //Проверка статуса
            if(grant.getStatusFrom() != null && grant.getStatusTo() != null){
                //Если статус сущности входит в диапозон статусов, то на сущность распостраняются права доступа
                if(statusIn(grant, entity.getStatus())){
                    status = true;
                }else {
                    status = false;
                }
            }
            //Проверка папки
            if(Objects.nonNull(grant.getFolder())){
                //Если это одна и та же папка или папка сущности является дочрней, то на сущность распостраняются права доступа grant
                if(Objects.nonNull(entity.getFolder()) && Objects.equals(grant.getFolder().getId(),entity.getFolder().getId()) || isChildFolder(grant.getFolder(), entity.getFolder())){
                    folder = true;
                }else {
                    folder = false;
                }
            }

            //Если условия для статуса и папки для конкретной сущности выполняются, то права распостраняются на эту сущность
            if (status && folder){
                //проверка исполнителя
                if(grant.getRead() == GrantRule.EXECUTOR){

                    if(Objects.equals(user.getPerson().getId(),(entity.getExecutor().getId()))){
                        entitlement.put("entity_read", true);
                        access.setRead(GrantRule.ALWAYS);
                    }
                }
                if(grant.getUpdate() == GrantRule.EXECUTOR){
                    if(Objects.equals(user.getPerson().getId(),(entity.getExecutor().getId()))){
                        entitlement.put("entity_update", true);
                        access.setUpdate(GrantRule.ALWAYS);
                    }
                }
                //проверка рабочей группы (Без дочерних)
                if(grant.getRead() == GrantRule.WORKGROUP){
                    if(isMember(entity.getWorkgroup(), user.getPerson())){
                        entitlement.put("entity_read", true);
                        access.setRead(GrantRule.ALWAYS);
                    }
                }
                if(grant.getUpdate() == GrantRule.WORKGROUP) {
                    if (isMember(entity.getWorkgroup(), user.getPerson())) {
                        entitlement.put("entity_update", true);
                        access.setUpdate(GrantRule.ALWAYS);
                    }
                }
                //проверка общих прав доступ
                if(grant.getRead() == GrantRule.ALWAYS) {
                    entitlement.put("entity_read", true);
                    access.setRead(GrantRule.ALWAYS);
                }
                if(grant.getUpdate() == GrantRule.ALWAYS){
                    entitlement.put("entity_update", true);
                    access.setUpdate(GrantRule.ALWAYS);
                }

                if(grant.getCreate() == GrantRule.ALWAYS){
                    entitlement.put("entity_create", true);
                    access.setCreate(GrantRule.ALWAYS);
                }
                if(grant.getDelete() == GrantRule.ALWAYS){
                    entitlement.put("entity_delete", true);
                    access.setCreate(GrantRule.ALWAYS);
                }

                //Получение и объединение атрибутов
                Map<String,AttributeGrantRule> attributeAccess = getAttributeAccess(grant, entityType);
                attributeAccess.forEach((k,v)-> System.out.println(k + " " + v));
                for(String field: attributeAccess.keySet()){
                    if(attributeAccessResult.get(field) == null){
                        attributeAccessResult.put(field, attributeAccess.get(field));
                    }else if(attributeAccessResult.get(field).getId() < attributeAccess.get(field).getId()){
                        attributeAccessResult.put(field, attributeAccess.get(field));
                    }
                }
            }
        }
        MutablePair<Grant, Map<String, AttributeGrantRule>> result = new MutablePair<>();
        result.setLeft(access);
        result.setRight(attributeAccessResult);

        return result;
    }

    /**
     * Проверка находится ли status в диапозон statusFrom - statusTo
     * @param grant описание прав доступа включая диапозон статусов
     * @param status статус для проверки
     * @return true если входит в диапозон, false если нет
     */
    private Boolean statusIn(Grant grant, EntityStatus status){
        if(Objects.isNull(status) || Objects.isNull(grant.getStatusFrom()) || Objects.isNull(grant.getStatusTo())
                || Objects.isNull(status.getOrder()) || Objects.isNull(grant.getStatusFrom().getOrder()) || Objects.isNull(grant.getStatusTo().getOrder())) return false;
        if((status.getOrder()>= grant.getStatusFrom().getOrder()) && (status.getOrder()<= grant.getStatusTo().getOrder()))
            return true;
        return false;
    }

    /**
     * Проверка является ли childFolder дочерней папкой parentFolder
     * @param parentfolder родительская папка
     * @param childFolder папка для проверки
     * @return true если является дочерней, false если нет
     */
    private Boolean isChildFolder(Folder parentfolder, Folder childFolder){
        if(Objects.isNull(parentfolder) || Objects.isNull(childFolder)) return false;
        Map<String, String> filter = new HashMap<>();
        filter.put("parentId", parentfolder.getId().toString());
        List<BaseCode> baseCodes = codeChildsDao.list(filter);
        for(BaseCode baseCode: baseCodes){
            if(Objects.equals(baseCode.getId(),childFolder.getId())){
                return true;
            }
        }
        return false;
    }

    /**
     * Проверка является ли user членом entityWorkgroup
     * @param entityWorkgroup рабочая группа исполнителя
     * @param person персона текущего пользователя
     * @return true если user входит, false если нет
     */
    private Boolean isMember(Workgroup entityWorkgroup, Person person){
        Map<String, String> filter = new HashMap<>();
        filter.put("person", person.getId().toString());
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

