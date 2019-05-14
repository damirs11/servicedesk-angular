package ru.it.sd.service;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.it.sd.dao.AbstractEntityDao;
import ru.it.sd.dao.RequiredFieldDao;
import ru.it.sd.meta.FieldMetaData;
import ru.it.sd.meta.MetaUtils;
import ru.it.sd.model.EntityType;
import ru.it.sd.model.HasAssignment;
import ru.it.sd.model.HasId;
import ru.it.sd.model.HasStatus;
import ru.it.sd.model.RequireField;
import ru.it.sd.util.EntityUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис получения обязатльных полей
 *
 * @author nsychev
 */
@Service
public class RequiredFieldService {

    private final ApplicationContext applicationContext;
    private final RequiredFieldDao requiredFieldDao;

    public RequiredFieldService(ApplicationContext applicationContext, RequiredFieldDao requiredFieldDao) {
        this.applicationContext = applicationContext;
        this.requiredFieldDao = requiredFieldDao;
    }

    /**
     * Метод получения обязательных полей
     *
     * @param id         идентификатор сущности
     * @param entityType тип сущности
     * @param statusId   идентификатор статуса(если есть)
     * @return список названий обязательных полей
     */
    public List<String> getRequiredFields(Long id, EntityType entityType, Long statusId) {
        Class<?> entityClass = EntityUtils.getEntityClass(entityType.getAlias());
        HasId entity = getDaoBean(entityClass).read(id, AbstractEntityDao.MapperMode.SIMPLEST);
        List<String> requiredFields = statusId == null || statusId == 0L ? getRequiredFields(entity, entityType) : getRequiredFields(entityType, statusId);
        if (entity instanceof HasAssignment) {
            List<String> assignmentRequiredFields = getRequiredFields(((HasAssignment) entity).getAssignment(), EntityType.ASSIGNMENT);
            assignmentRequiredFields.forEach(s -> requiredFields.add("assignment." + s));
        }
        return requiredFields;
    }

    private List<String> getRequiredFields(Object entity, EntityType entityType) {
        Long statusId = 0L;
        if (entity instanceof HasStatus) {
            statusId = ((HasStatus) entity).getStatus().getId();
        }
        return getRequiredFields(entityType, statusId);
    }

    private List<String> getRequiredFields(EntityType entityType, Long statusId) {
        Class<?> entityClass = EntityUtils.getEntityClass(entityType.getAlias());
        Map<String, String> filter = new HashMap<>();
        filter.put("entityTypeId", entityType.getId().toString());
        filter.put("statusId", statusId.toString());
        Collection<FieldMetaData> fieldMetaDataList = MetaUtils.getFieldsMetaData(entityClass).values();
        List<Long> attributes = fieldMetaDataList
                .stream()
                .map(FieldMetaData::getAttribute)
                .filter(attr -> attr >= 0)
                .collect(Collectors.toList());
        StringBuilder attributesString = new StringBuilder();
        boolean isFirst = true;
        for (Long attribute : attributes) {
            if (!isFirst) {
                attributesString.append(";");
            }
            attributesString.append(attribute);
            isFirst = false;
        }
        filter.put("id", attributesString.toString());
        List<Long> requireFieldList = requiredFieldDao.list(filter).stream()
                .map(RequireField::getId)
                .collect(Collectors.toList());
        return fieldMetaDataList.stream()
                .filter(fieldMetaData -> !fieldMetaData.getType().getName().equals("java.lang.Boolean") && !fieldMetaData.getName().equals("boolean"))
                .filter(fieldMetaData -> requireFieldList.contains(fieldMetaData.getAttribute()))
                .map(FieldMetaData::getName)
                .collect(Collectors.toList());
    }

    /**
     * Получение бина дао по классу сущности
     *
     * @param aClass класс сущности
     * @return бин дао
     */
    private AbstractEntityDao getDaoBean(Class aClass) {
        Map<String, AbstractEntityDao> beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, AbstractEntityDao.class);
        AbstractEntityDao entityDao = beans.values().stream().filter(abstractEntityDao -> getEntityClass(abstractEntityDao) == aClass).findFirst().orElse(null);
        if (entityDao == null) {
            throw new NoSuchBeanDefinitionException("Не найден дао для данного типа");
        }
        return entityDao;
    }

    /**
     * Возвращает класс обобщения
     *
     * @param entity исходный класс
     * @return класс обобщения
     */
    private Class getEntityClass(Object entity) {
        Type type = entity.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) type;
            if (t.getRawType() == AbstractEntityDao.class) {
                return (Class) t.getActualTypeArguments()[0];
            }
        }
        return null;
    }
}
