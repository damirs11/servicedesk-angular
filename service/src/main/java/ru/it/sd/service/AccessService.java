package ru.it.sd.service;

import org.springframework.stereotype.Service;
import ru.it.sd.dao.*;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.*;
import ru.it.sd.util.EntityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccessService {

    private AttributeAccessDao attributeAccessDao;
    private GrantDao grantDao;
    private SecurityService securityService;
    private CodeDao codeDao;
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
     * Функция получения и объединения прав доступа на все атрибуты сущности
     * @param entityEntitlement права доступа к сущности @See {@link #getAttributeEntitlement(Map, EntityType)}
     * @param entityType тип сущности
     * @return возращает мапу со списком атрибутов и их доступности(0-спрятать, 1-читать, 2-писать)
     */
    public Map<String, Integer> getAttributeEntitlement(Map<String, Boolean> entityEntitlement, EntityType entityType){
        Map<String,Integer> attributeEntitlement = new HashMap<>();
        User user = securityService.getCurrentUser();
        Class clazz = EntityUtils.getEntityClass(entityType.getAlias());
        Map<String, FieldMetaData> fieldMetaDataMap = MetaUtils.getFieldsMetaData(clazz);

        //Получение количество прав доступа для конкретной сущности и пользователя
        Map<String, String> filter = new HashMap<>();
        filter.put("accountId",String.valueOf(user.getId()));
        filter.put("entityType", entityType.getId().toString());
        Integer grantsCount = grantDao.count(filter);
        filter.clear();

        for(FieldMetaData fmd: fieldMetaDataMap.values()){
            //Если нет доступа на чтение, то записываем 0(спрятать)
            if(!entityEntitlement.get("entity_read")) attributeEntitlement.put(fmd.getName(), 0);
            //Если записан id атрибута в моделе
            if(fmd.getAttribute() != Long.MIN_VALUE){
                filter = new HashMap<>();
                filter.put("accountId",String.valueOf(user.getId()));
                filter.put("attributeId", String.valueOf(fmd.getAttribute()));
                List<AttributeAccess> attributeAccessList = attributeAccessDao.list(filter);
                //Проверка прав для атрибутов
                //Если есть доступ на чтение, но нет доступа на запись то проставляем значение из attributeAccessList
                if(entityEntitlement.get("entity_read") && !entityEntitlement.get("entity_update")){
                    if(isReadAvailable(attributeAccessList)){
                        attributeEntitlement.put(fmd.getName(), 1);
                    }else{
                        attributeEntitlement.put(fmd.getName(), 0);
                    }
                }
                //Если есть доступ и на чтение и на запись
                if(entityEntitlement.get("entity_read") && entityEntitlement.get("entity_update")){
                    //Если количество прав доступа у сущности и количество прав доступа у атрибута(для конкретного пользователя) отличается,
                    // то значит есть поля со стандартным значением - null(писать), иначе проверяем доступ на чтение
                    if(grantsCount != attributeAccessList.size()){
                        attributeEntitlement.put(fmd.getName(), 2);
                    }else if(isReadAvailable(attributeAccessList)){
                        attributeEntitlement.put(fmd.getName(), 1);
                    }else{
                        attributeEntitlement.put(fmd.getName(), 0);
                    }
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
    public Map<String, Boolean> getEntityEntitlement(Change entity){
        User user = securityService.getCurrentUser();
        Long accountId = user.getId();
        Map<String, String> filter = new HashMap<>();
        filter.put("accountId", accountId.toString());
        //todo исправить привязку к конкретной сущности
        filter.put("entityType", EntityType.getByClass(Change.class).getId().toString());
        List<Grant> grantList = grantDao.list(filter);

        //Результат проверки прав доступа и установленные значения по умолчанию
        Map<String, Boolean> entitlement = new HashMap<>();
        entitlement.put("entity_read", false);
        entitlement.put("entity_update", false);
        entitlement.put("entity_create", false);
        entitlement.put("entity_delete", false);

        //Цикл по всем правам доступа к сущности(ena)
        for(Grant grant: grantList){
            //Необходимость проверки условий
            Boolean status = false;
            Boolean folder = false;
            //Проверка статуса
            if(grant.getStatusFrom() != null && grant.getStatusTo() != null){
                //Если статус сущности входит в диапозон статусов, то на сущность распостраняются права доступа
                if(statusIn(grant, entity.getStatus())){
                    status = true;
                }else {
                    status = false;
                }
            } else {
                //Если статус проверять не надо то status автоматически становится выполеным условием
                status = true;
            }
            //Проверка папки
            if(grant.getFolder() != null){
                //Если это одна и та же папка или папка сущности является дочрней, то на сущность распостраняются права доступа grant
                if((grant.getFolder().getId() == entity.getFolder().getId()) || isChildFolder(grant.getFolder(), entity.getFolder())){
                    folder = true;
                }else {
                    folder = false;
                }
            }else {
                //Если папку проверять не надо то доступ к папке считается автоматически выполеным условием
                folder= true;
            }
            //Если условия для статуса и папки для конкретной сущности выполняются, то права распостраняются на эту сущность
            if (status && folder){
                //проверка исполнителя
                if(grant.getRead() == GrantRule.EXECUTOR){
                    if(user.getPerson().getId() == entity.getExecutor().getId()){
                        entitlement.put("entity_read", true);
                    }
                }
                if(grant.getUpdate() == GrantRule.EXECUTOR){
                    if(user.getPerson().getId() == entity.getExecutor().getId()){
                        entitlement.put("entity_update", true);
                    }
                }
                //проверка рабочей группы (Без дочерних)
                if(grant.getRead() == GrantRule.WORKGROUP){
                    if(isMember(entity.getAssWorkgroup(), user.getPerson())){
                        entitlement.put("entity_read", true);
                    }
                }
                if(grant.getUpdate() == GrantRule.WORKGROUP) {
                    if (isMember(entity.getAssWorkgroup(), user.getPerson())) {
                        entitlement.put("entity_update", true);
                    }
                }
                //проверка общих прав доступ
                if(grant.getRead() == GrantRule.ALWAYS) {
                    entitlement.put("entity_read", true);
                }
                if(grant.getUpdate() == GrantRule.ALWAYS){
                    entitlement.put("entity_update", true);
                }

                if(grant.getCreate() == GrantRule.ALWAYS){
                    entitlement.put("entity_create", true);
                }
                if(grant.getDelete() == GrantRule.ALWAYS){
                    entitlement.put("entity_delete", true);
                }
            }
        }
        return entitlement;
    }

    /**
     * Проверка находится ли status в диапозон statusFrom - statusTo
     * @param grant описание прав доступа включая диапозон статусов
     * @param status статус для проверки
     * @return true если входит в диапозон, false если нет
     */
    private Boolean statusIn(Grant grant, EntityStatus status){
        if((status.getOrdering()>= grant.getStatusFrom().getOrdering()) && (status.getOrdering()<= grant.getStatusTo().getOrdering())){
            return true;
        }
        return false;
    }

    /**
     * Проверка является ли childFolder дочерней папкой parentFolder
     * @param parentfolder родительская папка
     * @param childFolder папка для проверки
     * @return true если является дочерней, false если нет
     */
    private Boolean isChildFolder(Folder parentfolder, Folder childFolder){
        Map<String, String> filter = new HashMap<>();
        filter.put("parentId", parentfolder.getId().toString());
        List<BaseCode> baseCodes = codeChildsDao.list(filter);
        for(BaseCode baseCode: baseCodes){
            if(baseCode.getId() == childFolder.getId()){
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
            if(workgroup.getId() == entityWorkgroup.getId()){
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

