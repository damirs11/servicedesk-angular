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

    private final RoleDao roleDao;

    public AccessService(GrantDao grantDao, SecurityService securityService, WorkgroupDao workgroupDao, CodeDao codeDao, RoleDao roleDao) {
        this.grantDao = grantDao;
        this.securityService = securityService;
        this.workgroupDao = workgroupDao;
        this.codeDao = codeDao;
        this.roleDao = roleDao;
    }

    /**
     * Получение списка всех грантов.
     * Используется кэш для оптимизации скорости работы
     *
     * @return список всех грантов
     */
    @Cacheable(cacheNames = "access")
    public List<Grant> getList() {
        return grantDao.list(new HashMap<>());
    }

    /**
     * Список отфильтрованных прав доступа
     *
     * @param user       пользователь
     * @param entityType тип сущности
     * @return список грантов для сущности
     */
    private List<Grant> getList(User user, EntityType entityType) {
        Map<String, String> userFilter = new HashMap<>();
        userFilter.put("accountId", String.valueOf(user.getId()));
        List<Long> roles = roleDao.list(userFilter, AbstractEntityDao.MapperMode.SIMPLEST).stream().map(Role::getId).collect(Collectors.toList());
        return getList().stream()
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
     * Находит результирующий грант по роли и иерархии папок
     *
     * @param role   роль
     * @param folder папка сущности
     * @param grants найденный набор прав
     * @return результирующий грант для роли
     */
    private Grant getCommonGrantByRole(Role role, Folder folder, List<Grant> grants) {

        //Список грантов для одной роли и текущей папки
        List<Grant> grantsByRole = grants.stream()
                .filter(grant -> grant.getRole().equals(role))
                .collect(Collectors.toList());
        Grant resultGrant = new Grant();
        resultGrant.setRole(role);
        BaseCode folder1 = folder;
        //Поднимаемся вверх по иерархи от папки сущности
        while (true) {
            BaseCode finalFolder = folder1;
            Grant foundGrant = grantsByRole.stream()
                    .filter(grant -> grant.getFolder() == finalFolder || (grant.getFolder() != null && grant.getFolder().equals(finalFolder)))
                    .findFirst()
                    .orElse(null);
            //Если грант не найден для текущей папки поднимаемся выше
            if (foundGrant == null) {
                folder1 = folder1.getParent();
                continue;
            }
            //Мержим права доступа в результирующий грант
            mergeGrant(resultGrant, foundGrant);
            if (foundGrant.getAttributeAccessList() != null && !foundGrant.getAttributeAccessList().isEmpty()) {
                resultGrant.getAttributeAccessList().addAll(foundGrant.getAttributeAccessList());
            }
            //Если дошли до вверха иерархии то выходим из цикла
            if (folder1 == null) break;
            //Поднимаемся вверх по иерархии
            folder1 = folder1.getParent();
        }
        return resultGrant;
    }

    /**
     * Мерджит найденный грант в результрующий
     *
     * @param resultGrant результирующй грант
     * @param foundGrant  найденный
     */
    private void mergeGrant(Grant resultGrant, Grant foundGrant) {
        if (resultGrant.getRead() == GrantRule.NONE) {
            resultGrant.setRead(foundGrant.getRead());
        }
        if (resultGrant.getUpdate() == GrantRule.NONE) {
            resultGrant.setUpdate(foundGrant.getUpdate());
        }
        if (resultGrant.getStatusFrom() == null && foundGrant.getUpdate() != GrantRule.NONE) {
            resultGrant.setStatusFrom(foundGrant.getStatusFrom());
        }
        if (resultGrant.getStatusTo() == null && foundGrant.getUpdate() != GrantRule.NONE) {
            resultGrant.setStatusTo(foundGrant.getStatusTo());
        }
        if (resultGrant.getCreate() == GrantRule.NONE) {
            resultGrant.setCreate(foundGrant.getCreate());
        }
        if (resultGrant.getDelete() == GrantRule.NONE) {
            resultGrant.setDelete(foundGrant.getDelete());
        }
        if (resultGrant.getHistoryCreate() == GrantRule.NONE) {
            resultGrant.setHistoryCreate(foundGrant.getHistoryCreate());
        }
        if (resultGrant.getHistoryRead() == GrantRule.NONE) {
            resultGrant.setHistoryRead(foundGrant.getHistoryRead());
        }
        if (resultGrant.getHistoryUpdate() == GrantRule.NONE) {
            resultGrant.setHistoryUpdate(foundGrant.getHistoryUpdate());
        }
        if (resultGrant.getHistoryDelete() == GrantRule.NONE) {
            resultGrant.setHistoryDelete(foundGrant.getHistoryDelete());
        }
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
        List<Grant> grantList = getList(user, entityType);
        List<Grant> grantsByRole = new ArrayList<>();
        for (Role role : user.getRoles()) {
            Grant commonGrantForRole = getCommonGrantByRole(role, entity.getFolder(), grantList);
            commonGrantForRole.setEntityType(entityType);
            grantsByRole.add(commonGrantForRole);
        }
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
        Grant resultGrant = new Grant();
        MutablePair<Grant, Map<String, AttributeGrantRule>> result = new MutablePair<>();
        Map<String, AttributeGrantRule> attributeAccessMap = new HashMap<>();
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
        resultGrant.setRead(findResultGrant(filterGrants(grantsByRole, readPredicates), Grant::getRead));
        //Находим результирующее грант для редактирования
        resultGrant.setUpdate(findResultGrant(filterGrants(grantsByRole, updatePredicates), Grant::getUpdate));
        //Находим результирующее грант для создания
        resultGrant.setCreate(findResultGrant(filterGrants(grantsByRole, null), Grant::getCreate));
        //Находим результирующее грант для удаления
        resultGrant.setDelete(findResultGrant(filterGrants(grantsByRole, null), Grant::getDelete));
        //Находим результирующее грант для создания истории
        resultGrant.setHistoryCreate(findResultGrant(filterGrants(grantsByRole, null), Grant::getHistoryCreate));
        //Находим результирующее грант для чтения истории
        resultGrant.setHistoryRead(findResultGrant(filterGrants(grantsByRole, null), Grant::getHistoryRead));
        //Находим результирующее грант для редактирования истории
        resultGrant.setHistoryUpdate(findResultGrant(filterGrants(grantsByRole, modifyHistoryPredicates), Grant::getHistoryUpdate));
        //Находим результирующее грант для удаления истории
        resultGrant.setHistoryDelete(findResultGrant(filterGrants(grantsByRole, deleteHistoryPredicates), Grant::getHistoryDelete));
        for (Grant grant : grantsByRole) {
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
            workgroups.addAll(workgroupDao.list(wgFilter, AbstractEntityDao.MapperMode.SIMPLEST));
        }
        //todo оптимизировать
        for (Workgroup workgroup : workgroups) {
            Map<String, String> wgFilter = new HashMap<>();
            wgFilter.put("workgroupId", workgroup.getId().toString());
            List<Long> ids = workgroupDao.list(wgFilter, AbstractEntityDao.MapperMode.SIMPLEST)
                    .stream()
                    .map(Workgroup::getId)
                    .collect(Collectors.toList());
            filterEntity.getWorkgroups().addAll(ids);
        }
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

