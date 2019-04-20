package ru.it.sd.service;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AttributeAccessDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.GrantDao;
import ru.it.sd.dao.RoleDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.AttributeAccess;
import ru.it.sd.model.AttributeGrantRule;
import ru.it.sd.model.BaseCode;
import ru.it.sd.model.EntityStatus;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.Folder;
import ru.it.sd.model.Grant;
import ru.it.sd.model.GrantRule;
import ru.it.sd.model.HasAssignment;
import ru.it.sd.model.HasFolder;
import ru.it.sd.model.HasStatus;
import ru.it.sd.model.Person;
import ru.it.sd.model.Role;
import ru.it.sd.model.User;
import ru.it.sd.model.Workgroup;
import ru.it.sd.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccessService {

    private static final Logger LOG = LoggerFactory.getLogger(AccessService.class);

    private AttributeAccessDao attributeAccessDao;
    private GrantDao grantDao;
    private SecurityService securityService;
    private CodeDao codeDao;
    private WorkgroupDao workgroupDao;

    private final RoleDao roleDao;

    public AccessService(AttributeAccessDao attributeAccessDao, GrantDao grantDao, SecurityService securityService, WorkgroupDao workgroupDao, CodeDao codeDao, RoleDao roleDao){
        this.attributeAccessDao = attributeAccessDao;
        this.grantDao = grantDao;
        this.securityService = securityService;
        this.workgroupDao = workgroupDao;
        this.codeDao = codeDao;
        this.roleDao = roleDao;
    }

    @Cacheable(cacheNames = "access")
    public List<Grant> getList() {
        return grantDao.list(new HashMap<>());
    }

    /**
     * Список отфильтрованных прав доступа
     * @param user
     * @param entityType
     * @return
     */
    private List<Grant> getList(User user, EntityType entityType) {
        Map<String, String> userFilter = new HashMap<>();
        userFilter.put("accountId", String.valueOf(user.getId()));
        List<Long> roles = roleDao.list(userFilter).stream().map(Role::getId).collect(Collectors.toList());
        return getList().stream()
                .filter(grant -> grant.getEntityType() == entityType && roles.contains(grant.getRole().getId()))
                .collect(Collectors.toList());
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
        List<AttributeAccess> attributeAccessList = grant.getAttributeAccessList();

        for(FieldMetaData fmd: fieldMetaDataMap.values()){
            //Если записан id атрибута в моделе
            if(!Objects.equals(fmd.getAttribute(),Long.MIN_VALUE)){
                //Если нет доступа на чтение, то записываем 0(спрятать)
                if(grant.getRead() == GrantRule.NONE) {
                    attributeEntitlement.put(fmd.getName(), AttributeGrantRule.HIDE);
                } else {
                    AttributeAccess attributeAccess;
                    if(isUpdate(attributeAccessList, fmd.getAttribute())){
                        attributeAccess = null;
                    }else {
                        attributeAccess = getAttributeAccess(attributeAccessList, fmd.getAttribute());
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
                }

            }
        }
        return attributeEntitlement;
    }

    private Boolean isUpdate(List<AttributeAccess> attributeAccesses, Long attributeId){
        for(AttributeAccess attributeAccess: attributeAccesses){
            if(attributeAccess.getAttributeId().equals(attributeId)){
               return false;
            }
        }
        return true;
    }

    private AttributeAccess getAttributeAccess(List<AttributeAccess> attributeAccesses, Long attributeId){
        for(AttributeAccess attributeAccess: attributeAccesses){
            if(attributeAccess.getAttributeId().equals(attributeId)){
                return attributeAccess;
            }
        }
        return null;
    }

    /**
     * Проверка всех прав доступа для конкретной сущности и текущего пользователя и объединение их
     * @param entity сущность для проверки прав доступа
     * @return {@link Grant} общие права доступа к конкретной сущности
     */
    public Pair<Grant, Map<String, AttributeGrantRule>> getEntityAccess(HasFolder entity){
        User user = securityService.getCurrentUser();
        EntityType entityType = EntityType.getByClass(entity.getClass());
        List<Grant> grantList = getList(user, entityType);

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

        List<BaseCode> parentFolders = new ArrayList<>();
        if(entity.getFolder() != null){
            Map<String, String> filter = new HashMap<>();
            filter.put("codeId", entity.getFolder().getId().toString());
            parentFolders = codeDao.list(filter);
        }
        //Результат проверки прав доступа атрибутов
        Map<String, AttributeGrantRule> attributeAccessMap = new HashMap<>();
        //Цикл по всем правам доступа к сущности(ena)
        for(Grant grant: grantList){
            //Если тип не определен, то пропускаем
            if (grant.getEntityType() == null || grant.getEntityType() != entityType) continue;
            //Необходимость проверки условий
            boolean folder = true;
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


    public void applyReadFilter(FilterMap filter, Class<? extends HasFolder> clazz) {
        List<Workgroup> workgroups = new ArrayList<>();
        final User user = securityService.getCurrentUser();
        final Person person = user.getPerson();
        EntityType entityType = EntityType.getByClass(clazz);
        List<Grant> grantList = getList(user, entityType);
        grantList = grantList.parallelStream().filter(grant -> grant.getRead() != GrantRule.NONE).collect(Collectors.toList());
        //Если нет прав доступа, то проставляем флаг
        if (grantList.isEmpty()) {
            AccessFilterEntity filterEntity = new AccessFilterEntity();
            filterEntity.setNoAccess(true);
            filter.getAccessFilter().add(filterEntity);
        }
        List<Grant> grantsWithoutFolder = grantList.stream().filter(grant -> grant.getFolder() == null).collect(Collectors.toList());
        for (Grant grant : grantsWithoutFolder) {
            AccessFilterEntity filterEntity = new AccessFilterEntity();
            switch (grant.getRead()) {
                case ALWAYS:
                    return;
                case EXECUTOR:
                    filterEntity.setExecutor(person.getId());
                    filter.getAccessFilter().add(filterEntity);
                    return;
                case WORKGROUP:
                    addWorkgroups(workgroups, filterEntity, person);
                    filter.getAccessFilter().add(filterEntity);
                    return;
            }
        }

        List<Grant> grantsWithFolder = grantList.stream().filter(grant -> grant.getFolder() != null).collect(Collectors.toList());
        for (Grant grant : grantsWithFolder) {
            AccessFilterEntity filterEntity = new AccessFilterEntity();
            addFolders(grant.getFolder().getId(), filterEntity);
            if (grant.getRead() == GrantRule.EXECUTOR) {
                filterEntity.setExecutor(person.getId());
            } else if (grant.getRead() == GrantRule.WORKGROUP) {
                 addWorkgroups(workgroups, filterEntity, person);
            }
            filter.getAccessFilter().add(filterEntity);
        }
    }

    private void addWorkgroups(List<Workgroup> workgroups, AccessFilterEntity filterEntity, Person person) {
        if (workgroups.isEmpty()) {
            Map<String, String> wgFilter = new HashMap<>();
            wgFilter.put("personId", person.getId().toString());
            workgroups.addAll(workgroupDao.list(wgFilter));
        }
        //todo оптимизировать
        for (Workgroup workgroup : workgroups) {
            Map<String, String> wgFilter = new HashMap<>();
            wgFilter.put("workgroupId", workgroup.getId().toString());
            List<Long> ids = workgroupDao.list(wgFilter).stream().map(Workgroup::getId).collect(Collectors.toList());
            filterEntity.getWorkgroups().addAll(ids);
        }
    }
    private void addFolders(Long folder, AccessFilterEntity filterEntity) {
        //todo оптимизировать
        Map<String, String> filter = new HashMap<>();
        filter.put("codeId", folder.toString());
        filter.put("child", null);
        filterEntity.getFolders().addAll(codeDao.list(filter).stream().map(BaseCode::getId).collect(Collectors.toList()));
    }

    /**
     * Определяет есть ли права на чтение и создание по типу сущности
     * @param entityType тип сущности
     * @return {@link Grant} права на чтение и создание
     */
    public Grant getAccess(EntityType entityType){
        User user = securityService.getCurrentUser();
        Long accountId = user.getId();
        Grant grant = new Grant();
        Map<String, String> filter = new HashMap<>();
        filter.put("entityId", entityType.getId().toString());
        filter.put("accountId",accountId.toString());
        filter.put("read", "");
        //На чтение
        Integer grantsCount = grantDao.count(filter);
        if(grantsCount > 0) grant.setRead(GrantRule.ALWAYS);
        filter.put("create", "");
        //На чтение и создание
        grantsCount = grantDao.count(filter);
        if(grantsCount > 0) grant.setCreate(GrantRule.ALWAYS);
        return grant;
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
                    if (hasAssignment.getAssignment() != null && hasAssignment.getAssignment().getExecutor() != null) {
                        if (Objects.equals(user.getPerson().getId(), (hasAssignment.getAssignment().getExecutor().getId()))) {
                            entityAccess.setRead(GrantRule.EXECUTOR);
                        }
                    }
                }
            }break;
            case WORKGROUP:{
                if (entity instanceof HasAssignment) {
                    HasAssignment hasAssignment = (HasAssignment) entity;
                    if (hasAssignment.getAssignment() != null && hasAssignment.getAssignment().getWorkgroup() != null) {
                        if (isMember(hasAssignment.getAssignment().getWorkgroup(), user.getPerson())) {
                            entityAccess.setRead(GrantRule.WORKGROUP);
                        }
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
                    if (Objects.equals(user.getPerson().getId(), (hasAssignment.getAssignment().getExecutor().getId()))) {
                        entityAccess.setUpdate(GrantRule.EXECUTOR);
                    }
                }
            }break;
            case WORKGROUP:{
                if (entity instanceof HasAssignment) {
                    HasAssignment hasAssignment = (HasAssignment) entity;
                    if (isMember(hasAssignment.getAssignment().getWorkgroup(), user.getPerson())) {
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
        filter.put("personId", executor.getId().toString());
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

