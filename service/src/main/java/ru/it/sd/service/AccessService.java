package ru.it.sd.service;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.CodeDao;
import ru.it.sd.dao.GrantDao;
import ru.it.sd.dao.RoleDao;
import ru.it.sd.dao.WorkgroupDao;
import ru.it.sd.dao.utils.AccessFilterEntity;
import ru.it.sd.dao.utils.FilterMap;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.Assignment;
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
import ru.it.sd.service.utils.Hierarchy;
import ru.it.sd.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccessService {

    private static final Logger LOG = LoggerFactory.getLogger(AccessService.class);

    private GrantDao grantDao;
    private SecurityService securityService;
    private CodeDao codeDao;
    private WorkgroupDao workgroupDao;
    private final FolderService folderService;

    private final RoleDao roleDao;

    public AccessService(GrantDao grantDao, SecurityService securityService, WorkgroupDao workgroupDao, CodeDao codeDao, RoleDao roleDao, FolderService folderService) {
        this.grantDao = grantDao;
        this.securityService = securityService;
        this.workgroupDao = workgroupDao;
        this.codeDao = codeDao;
        this.roleDao = roleDao;
        this.folderService = folderService;
    }

    /**
     * Получение списка всех грантов.
     * Используется кэш для оптимизации скорости работы
     *
     * @return список всех грантов
     */
    //@Cacheable(cacheNames = "access")
    public List<Grant> getList2() {
        List<Grant> list = grantDao.list(new HashMap<>());
        return list;
    }

    @Cacheable(cacheNames = "access", sync = true)
    public Map<EntityType, Map<Role, Hierarchy>> getAccess() {
        List<Grant> list = grantDao.list(new HashMap<>());
        List<Role> roles = roleDao.list(new HashMap<>());
        Map<String, String> filter = new HashMap<>();
        filter.put("simplest", "");
        List<Folder> folders = folderService.list(filter);
        Map<EntityType, Map<Role, Hierarchy>> hierarchies = new HashMap<>();
        for (EntityType entityType : EntityType.values()) {
            List<Grant> grants = list.stream().filter(grant -> grant.getEntityType() == entityType).collect(Collectors.toList());
            Map<Role, Hierarchy> hierarchies1 = new HashMap<>();
            for (Role role : roles) {
                List<Grant> grantList = grants.stream().filter(grant -> role.getId().equals(grant.getRole().getId())).collect(Collectors.toList());
                hierarchies1.put(role, buildFolderHierarchy(folders, grantList, entityType, role));
            }
            hierarchies.put(entityType, hierarchies1);
        }
        return hierarchies;
    }

    /**
     * Получение мапы иерархий прав доступа по сущности
     *
     * @param entityType тип сущности
     * @return мапа иерархий прав доступа по ролям
     */
    private Map<Role, Hierarchy> getAccessByEntityType(EntityType entityType) {
        return getAccess().get(entityType);
    }

    /**
     * Поиск иерархий прав доступа по ролям
     *
     * @param roles      роли
     * @param entityType тип сущности
     * @return список иерархий прав доступа
     */
    private List<Hierarchy> getAccessList(List<Role> roles, EntityType entityType) {
        return getAccessByEntityType(entityType).values().stream().filter(hierarchy -> roles.contains(hierarchy.getRole())).collect(Collectors.toList());
    }

    /**
     * Построение иерархии прав доступа для конкретной роли
     *
     * @param folders    все папки
     * @param grants     список прав доступа отфильтрованный по роли и типу сущности
     * @param entityType тип сущности
     * @param role       роль
     * @return иерархию прав доступа
     */
    private Hierarchy buildFolderHierarchy(List<Folder> folders, List<Grant> grants, EntityType entityType, Role role) {
        Hierarchy hierarchy = new Hierarchy();
        hierarchy.setFolder(null);
        hierarchy.setRole(role);
        hierarchy.setGrant(grants.stream().filter(grant -> grant.getFolder() == null).findFirst().orElse(null));
        hierarchy.setEntityType(entityType);
        for (Folder folder : folders) {
            if (folder.getParent() == null) {
                hierarchy.setChilds(buildFolderHierarchy(hierarchy, folders, grants));
            }
        }
        return hierarchy;
    }

    private List<Hierarchy> buildFolderHierarchy(Hierarchy hierarchy, List<Folder> folders, List<Grant> grants) {
        List<Hierarchy> hierarchies = new ArrayList<>();
        Folder parentFolder = hierarchy.getFolder();

        for (Folder folder : folders) {
            if (folder != parentFolder && ((parentFolder == null && folder.getParent() == null) || (folder.getParent() != null && folder.getParent().equals(parentFolder)))) {
                Hierarchy childHierarchy = new Hierarchy();
                Grant grant = grants.stream().filter(g -> folder.equals(g.getFolder())).findFirst().orElse(hierarchy.getGrant() != null ? hierarchy.getGrant().clone() : null);
                if (grant != null) {
                    grant = grant.clone();
                }
                mergeGrant(grant, hierarchy.getGrant());
                childHierarchy.setGrant(grant);
                childHierarchy.setFolder(folder);
                childHierarchy.setEntityType(hierarchy.getEntityType());
                childHierarchy.setParent(hierarchy);
                childHierarchy.setChilds(buildFolderHierarchy(childHierarchy, folders, grants));
                hierarchies.add(childHierarchy);
            }
        }
        return hierarchies;
    }

    /**
     * Мерджит найденный грант в результрующий
     *
     * @param foundGrant родительский грант
     * @param parentGrant  найденный
     */
    private void mergeGrant(Grant foundGrant, Grant parentGrant) {
        if (foundGrant == null || parentGrant == null) return;
        if (foundGrant.getRead() == GrantRule.NONE) {
            foundGrant.setRead(parentGrant.getRead());
        }
        if (foundGrant.getUpdate() == GrantRule.NONE) {
            foundGrant.setUpdate(parentGrant.getUpdate());
        }
        if (foundGrant.getStatusFrom() != null && parentGrant.getUpdate() != GrantRule.NONE) {
            foundGrant.setStatusFrom(parentGrant.getStatusFrom());
        }
        if (foundGrant.getStatusTo() != null && parentGrant.getUpdate() != GrantRule.NONE) {
            foundGrant.setStatusTo(parentGrant.getStatusTo());
        }
        if (foundGrant.getCreate() == GrantRule.NONE) {
            foundGrant.setCreate(parentGrant.getCreate());
        }
        if (foundGrant.getDelete() == GrantRule.NONE) {
            foundGrant.setDelete(parentGrant.getDelete());
        }
        if (foundGrant.getHistoryCreate() == GrantRule.NONE) {
            foundGrant.setHistoryCreate(parentGrant.getHistoryCreate());
        }
        if (foundGrant.getHistoryRead() == GrantRule.NONE) {
            foundGrant.setHistoryRead(parentGrant.getHistoryRead());
        }
        if (foundGrant.getHistoryUpdate() == GrantRule.NONE) {
            foundGrant.setHistoryUpdate(parentGrant.getHistoryUpdate());
        }
        if (foundGrant.getHistoryDelete() == GrantRule.NONE) {
            foundGrant.setHistoryDelete(parentGrant.getHistoryDelete());
        }
        if (foundGrant.getAttributeAccessList() == null || foundGrant.getAttributeAccessList().isEmpty()) {
            foundGrant.setAttributeAccessList(parentGrant.getAttributeAccessList());
        }
    }

    /**
     * Список отфильтрованных прав доступа
     *
     * @param user       пользователь
     * @param entityType тип сущности
     * @return список грантов для сущности
     */
    private List<Grant> getList2(User user, EntityType entityType) {
        List<Long> roles = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        return getList2().stream()
                .filter(grant -> grant.getEntityType() == entityType && roles.contains(grant.getRole().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Функция получения прав доступа на все атрибуты сущности по правам доступа {@link Grant}
     *
     * @param grant права доступа
     * @return возращает мапу со списком атрибутов и их доступности {@link AttributeGrantRule}
     */
    private Map<String, AttributeGrantRule> getAttributeAccess(Grant grant) {
        //todo оптимизировать
        Map<String, AttributeGrantRule> attributeEntitlement = new HashMap<>();
        Class clazz = EntityUtils.getEntityClass(grant.getEntityType().getAlias());
        Map<String, FieldMetaData> fieldMetaDataMap = MetaUtils.getFieldsMetaData(clazz);
        List<AttributeAccess> attributeAccessList = grant.getAttributeAccessList();
        for (FieldMetaData fmd : fieldMetaDataMap.values()) {
            //Если записан id атрибута в моделе
            if (!Objects.equals(fmd.getAttribute(), Long.MIN_VALUE)) {
                //Если нет доступа на чтение, то записываем 0(спрятать)
                if (grant.getRead() == GrantRule.NONE) {
                    attributeEntitlement.put(fmd.getName(), AttributeGrantRule.HIDE);
                } else {
                    AttributeAccess attributeAccess;
                    if (isUpdate(attributeAccessList, fmd.getAttribute())) {
                        attributeAccess = null;
                    } else {
                        attributeAccess = getAttributeAccess(attributeAccessList, fmd.getAttribute());
                    }
                    //Проверка прав для атрибутов

                    if (attributeAccess == null && grant.getUpdate() != GrantRule.NONE) {//Если есть права доступа на редактирование и редактирование атрибута
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.UPDATE);
                    } else if (attributeAccess == null) {//Если есть права доступа на редактирование атрибута, но нет на редактирование сущности
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.READ);
                    } else if (!attributeAccess.getModify()) {//Если есть права доступа на чтение атрибутов
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.READ);
                    } else {
                        attributeEntitlement.put(fmd.getName(), AttributeGrantRule.HIDE);
                    }
                }

            }
        }
        return attributeEntitlement;
    }

    private Boolean isUpdate(List<AttributeAccess> attributeAccesses, Long attributeId) {
        for (AttributeAccess attributeAccess : attributeAccesses) {
            if (attributeAccess.getAttributeId().equals(attributeId)) {
                return false;
            }
        }
        return true;
    }

    private AttributeAccess getAttributeAccess(List<AttributeAccess> attributeAccesses, Long attributeId) {
        for (AttributeAccess attributeAccess : attributeAccesses) {
            if (attributeAccess.getAttributeId().equals(attributeId)) {
                return attributeAccess;
            }
        }
        return null;
    }

    /**
     * Проверка гранта по списку предикатов
     *
     * @param grant      грант
     * @param predicates предикаты
     * @return true если грант подходит по условиям, инчае false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkGrant(Grant grant, List<Predicate<Grant>> predicates) {
        for (Predicate<Grant> predicate : predicates) {
            if (!predicate.test(grant)) {
                return false;
            }
        }
        return true;
    }

    private GrantRule findResultGrant(List<Grant> grants, Function<Grant, GrantRule> function) {
        GrantRule resultGrantRule = GrantRule.NONE;
        for (Grant grant : grants) {
            if (function.apply(grant).getOrder() > resultGrantRule.getOrder()) {
                resultGrantRule = function.apply(grant);
            }
        }
        return resultGrantRule;
    }

    private List<Grant> filterGrants(List<Grant> grants, List<Predicate<Grant>> predicates) {
        if (predicates == null || predicates.isEmpty()) {
            return grants;
        }
        Stream<Grant> stream = grants.stream();
        for (Predicate<Grant> predicate : predicates) {
            stream = stream.filter(predicate);
        }
        return stream.collect(Collectors.toList());
    }

    /**
     * Проверка всех прав доступа для конкретной сущности и текущего пользователя и объединение их
     *
     * @param entity сущность для проверки прав доступа
     * @return {@link Grant} общие права доступа к конкретной сущности
     */
    public Pair<Grant, Map<String, AttributeGrantRule>> getEntityAccess(HasFolder entity) {
        User user = securityService.getCurrentUser();
        EntityType entityType = EntityType.getByClass(entity.getClass());
        Folder folder = entity.getFolder();
        long startTime = System.currentTimeMillis();
        List<Hierarchy> accessList = getAccessList(user.getRoles(), entityType);
        LOG.info("Built hierarchy {}", System.currentTimeMillis() - startTime);
        //является исполнителем
        boolean isExecutor = false;
        //является членом рабочей группы
        boolean isMemberOfWorkgroup = false;
        //Проставляем значения для isExecutor && isMemberOfWorkgroup
        if (entity instanceof HasAssignment) {
            HasAssignment entityWithAssignment = (HasAssignment) entity;
            if (entityWithAssignment.getAssignment() != null && user.getPerson() != null) {
                Assignment assignment = entityWithAssignment.getAssignment();
                if (assignment.getExecutor() != null && assignment.getExecutor().getId().equals(user.getPerson().getId())) {
                    isExecutor = true;
                    isMemberOfWorkgroup = true;
                } else if (assignment.getWorkgroup() != null && isMember(assignment.getWorkgroup(), user.getPerson())) {
                    isMemberOfWorkgroup = true;
                }
            }
        }
        List<Grant> grants = new ArrayList<>();
        for (Hierarchy hierarchy : accessList) {
            Hierarchy byFolder = hierarchy.findByFolder(folder);
            if (byFolder != null) {
                grants.add(byFolder.getGrant());
            } else if (hierarchy.getGrant() != null) {
                grants.add(hierarchy.getGrant());
            }
        }
        MutablePair<Grant, Map<String, AttributeGrantRule>> result = new MutablePair<>();
        Map<String, AttributeGrantRule> attributeAccessMap = new HashMap<>();
        Grant resultGrant = new Grant();
        //todo подумать как сделать работу с предикатоми посимпотичнее
        //Набор предикатов для фильтрации прав чтения
        List<Predicate<Grant>> readPredicates = new ArrayList<>();
        //Набор предикатов для фильтрации прав редактирования
        List<Predicate<Grant>> updatePredicates = new ArrayList<>();
        //Набор предикатов для удаления истории
        List<Predicate<Grant>> deleteHistoryPredicates = new ArrayList<>();
        //Набор предикатов для редактрования истории
        List<Predicate<Grant>> modifyHistoryPredicates = new ArrayList<>();
        readPredicates.add(grant -> grant.getRead() != GrantRule.NONE);
        deleteHistoryPredicates.add(grant -> grant.getHistoryDelete() != GrantRule.NONE);
        modifyHistoryPredicates.add(grant -> grant.getHistoryUpdate() != GrantRule.NONE);
        updatePredicates.add(grant -> grant.getUpdate() != GrantRule.NONE);
        updatePredicates.add(grant -> statusBetween(grant, entity));
        //todo узнать учавствует ли статус при редактировании истории
        if (!isMemberOfWorkgroup) {
            //Если не является членом группы, то отсеиваем права для группы и исполнителя
            readPredicates.add(grant -> grant.getRead() != GrantRule.WORKGROUP);
            readPredicates.add(grant -> grant.getRead() != GrantRule.EXECUTOR);
            updatePredicates.add(grant -> grant.getUpdate() != GrantRule.WORKGROUP);
            updatePredicates.add(grant -> grant.getUpdate() != GrantRule.EXECUTOR);
            deleteHistoryPredicates.add(grant -> grant.getHistoryDelete() != GrantRule.WORKGROUP);
            deleteHistoryPredicates.add(grant -> grant.getHistoryDelete() != GrantRule.EXECUTOR);
            modifyHistoryPredicates.add(grant -> grant.getHistoryUpdate() != GrantRule.WORKGROUP);
            modifyHistoryPredicates.add(grant -> grant.getHistoryUpdate() != GrantRule.EXECUTOR);

        } else if (!isExecutor) {
            //Если не является исполнителем, то отсеиваем права для исполнителя
            readPredicates.add(grant -> grant.getRead() != GrantRule.EXECUTOR);
            updatePredicates.add(grant -> grant.getUpdate() != GrantRule.EXECUTOR);
            deleteHistoryPredicates.add(grant -> grant.getHistoryDelete() != GrantRule.EXECUTOR);
            modifyHistoryPredicates.add(grant -> grant.getHistoryUpdate() != GrantRule.EXECUTOR);
        }
        //Находим результирующее чтение
        resultGrant.setRead(findResultGrant(filterGrants(grants, readPredicates), Grant::getRead));
        //Находим результирующее грант для редактирования
        resultGrant.setUpdate(findResultGrant(filterGrants(grants, updatePredicates), Grant::getUpdate));
        //Находим результирующее грант для создания
        resultGrant.setCreate(findResultGrant(filterGrants(grants, null), Grant::getCreate));
        //Находим результирующее грант для удаления
        resultGrant.setDelete(findResultGrant(filterGrants(grants, null), Grant::getDelete));
        //Находим результирующее грант для создания истории
        resultGrant.setHistoryCreate(findResultGrant(filterGrants(grants, null), Grant::getHistoryCreate));
        //Находим результирующее грант для чтения истории
        resultGrant.setHistoryRead(findResultGrant(filterGrants(grants, null), Grant::getHistoryRead));
        //Находим результирующее грант для редактирования истории
        resultGrant.setHistoryUpdate(findResultGrant(filterGrants(grants, modifyHistoryPredicates), Grant::getHistoryUpdate));
        //Находим результирующее грант для удаления истории
        resultGrant.setHistoryDelete(findResultGrant(filterGrants(grants, deleteHistoryPredicates), Grant::getHistoryDelete));
        for (Grant grant : grants) {
            Grant clone = grant.clone();
            //Если грант не удовлетворяет условиям, то проставляем NONE
            if (!checkGrant(clone, readPredicates)) {
                clone.setRead(GrantRule.NONE);
            }
            if (!checkGrant(clone, updatePredicates)) {
                clone.setUpdate(GrantRule.NONE);
            }
            //Мердж атрибутов
            Map<String, AttributeGrantRule> attributeAccess = getAttributeAccess(grant);
            for (String field : attributeAccess.keySet()) {
                if (attributeAccessMap.get(field) == null) {
                    attributeAccessMap.put(field, attributeAccess.get(field));
                } else if (attributeAccessMap.get(field).getId() < attributeAccess.get(field).getId()) {
                    attributeAccessMap.put(field, attributeAccess.get(field));
                }
            }
        }
        result.setRight(attributeAccessMap);
        result.setLeft(resultGrant);
        return result;
    }


    public void applyReadFilter(FilterMap filter, Class<? extends HasFolder> clazz) {
        long start = System.currentTimeMillis();
        final User user = securityService.getCurrentUser();
        final Person person = user.getPerson();
        EntityType entityType = EntityType.getByClass(clazz);
        List<Hierarchy> accessList = getAccessList(user.getRoles(), entityType);
        for (Hierarchy hierarchy : accessList) {
            getFoldersForRead(hierarchy, filter, person);
        }
        //Делаем дистинкт
        List<AccessFilterEntity> distinctList = filter.getAccessFilter().stream().distinct().collect(Collectors.toList());
        //Очищаем список
        filter.getAccessFilter().clear();
        //Добавляем обновленый
        filter.getAccessFilter().addAll(distinctList);

        filter.getAccessFilter().removeIf(AccessFilterEntity::getNoAccess);
        if (filter.getAccessFilter().isEmpty()) {
            AccessFilterEntity accessFilterEntity = new AccessFilterEntity();
            accessFilterEntity.setNoAccess(true);
            filter.getAccessFilter().add(accessFilterEntity);
        }
        if (filter.getAccessFilter().removeIf(filterEntity -> filterEntity.getFolders().isEmpty() && !filterEntity.getNoAccess())) {
            AccessFilterEntity accessFilterEntity = new AccessFilterEntity();
            filter.getAccessFilter().add(accessFilterEntity);
        }
        LOG.info("Get access for list for {}ms", System.currentTimeMillis() - start);
    }

    private void getFoldersForRead(Hierarchy hierarchy, FilterMap filterMap, Person person) {
        AccessFilterEntity filterEntity = new AccessFilterEntity();
        AccessFilterEntity alwaysAccess = filterMap.getAccessFilter().stream().filter(filterEntity1 -> filterEntity1.getFolders().isEmpty() && !filterEntity1.getNoAccess()).findFirst().orElse(null);
        if ((hierarchy.getGrant().getFolder() == null && hierarchy.getGrant().getRead() == GrantRule.ALWAYS) || alwaysAccess != null) {
            filterMap.getAccessFilter().add(filterEntity);
            return;
        }
        if (hierarchy.getGrant() != null) {
            switch (hierarchy.getGrant().getRead()) {
                case NONE:
                    filterEntity.setNoAccess(true);
                    break;
                case EXECUTOR:
                    filterEntity.setExecutor(person.getId());
                    break;
                case WORKGROUP:
                    filterEntity.getWorkgroups().addAll(getWorkgroup(person).stream().map(Workgroup::getId).collect(Collectors.toList()));
            }
            if (!filterEntity.getNoAccess() && hierarchy.getGrant().getFolder() != null) {
                filterEntity.getFolders().add(hierarchy.getGrant().getFolder().getId());
            }
            filterMap.getAccessFilter().add(filterEntity);
        }
        for (Hierarchy child : hierarchy.getChilds()) {
            getFoldersForRead(child, filterMap, person);
        }
    }

    private List<Workgroup> getWorkgroup(Person person) {
        Map<String, String> wgFilter = new HashMap<>();
        wgFilter.put("personId", person.getId().toString());
        return workgroupDao.list(wgFilter, AbstractEntityDao.MapperMode.SIMPLEST);
    }

    private void addFolders(Long folder, AccessFilterEntity filterEntity) {
        //todo оптимизировать
        Map<String, String> filter = new HashMap<>();
        filter.put("codeId", folder.toString());
        filter.put("child", null);
        filterEntity.getFolders().addAll(codeDao.list(filter, AbstractEntityDao.MapperMode.SIMPLEST).stream().map(BaseCode::getId).collect(Collectors.toList()));
    }

    /**
     * Определяет есть ли права на чтение и создание по типу сущности
     *
     * @param entityType тип сущности
     * @return {@link Grant} права на чтение и создание
     */
    public Grant getAccess(EntityType entityType) {
        User user = securityService.getCurrentUser();
        Long accountId = user.getId();
        Grant grant = new Grant();
        Map<String, String> filter = new HashMap<>();
        filter.put("entityId", entityType.getId().toString());
        filter.put("accountId", accountId.toString());
        filter.put("read", "");
        //На чтение
        Integer grantsCount = grantDao.count(filter);
        if (grantsCount > 0) grant.setRead(GrantRule.ALWAYS);
        filter.put("create", "");
        //На чтение и создание
        grantsCount = grantDao.count(filter);
        if (grantsCount > 0) grant.setCreate(GrantRule.ALWAYS);
        return grant;
    }

    /**
     * Проверка находится ли status в диапозон statusFrom - statusTo
     *
     * @param grant  описание прав доступа включая диапозон статусов
     * @param status статус для проверки
     * @return true если входит в диапозон, false если нет
     */
    private Boolean statusBetween(Grant grant, EntityStatus status) {
        boolean result = true;
        if (grant.getStatusFrom() != null && grant.getStatusFrom().getOrder() > status.getOrder()) result = false;
        if (grant.getStatusTo() != null && grant.getStatusTo().getOrder() < status.getOrder()) result = false;
        return result;
    }

    private Boolean statusBetween(Grant grant, HasFolder entity) {
        if (!(entity instanceof HasStatus)) return true;
        EntityStatus status = ((HasStatus) entity).getStatus();
        return statusBetween(grant, status);
    }

    /**
     * Проверка является ли user членом entityWorkgroup
     *
     * @param entityWorkgroup рабочая группа исполнителя
     * @param executor        персона текущего пользователя
     * @return true если user входит, false если нет
     */
    private Boolean isMember(Workgroup entityWorkgroup, Person executor) {
        Map<String, String> filter = new HashMap<>();
        filter.put("personId", executor.getId().toString());
        List<Workgroup> workgroups = workgroupDao.list(filter, AbstractEntityDao.MapperMode.SIMPLEST);
        for (Workgroup workgroup : workgroups) {
            if (Objects.equals(workgroup.getId(), entityWorkgroup.getId())) {
                return true;
            }
        }
        return false;
    }

}

