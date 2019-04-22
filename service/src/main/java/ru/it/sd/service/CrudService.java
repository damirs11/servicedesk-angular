package ru.it.sd.service;

import ru.it.sd.model.HasId;

import java.util.Set;

/**
 * Универсальный сервис предоставляющий оcновные операции
 * Create - Read - Update - Delete - List для всех сущностей.
 *
 * @author quadrix
 * @since 22.05.2017
 */
public abstract class CrudService<T extends HasId> extends ReadService<T> {

    /**
     * Создает объект сущности.
     *
     * @param entity сущность
     * @return идентификатор созданной сущности
     */
    //@CachePut(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #entity.getId()")
    public abstract T create(T entity);

    /**
     * Обновляет все поля объекта сущности в БД. Поиск объекта производится по полю id.
     *
     * @param entity сущность
     */
    //@CachePut(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #entity.getId()")
    public abstract T update(T entity);

    /**
     * Удаляет сущность из БД.
     *
     * @param id        идентификатор сущности
     */
    //@CacheEvict(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #id")
    public abstract void delete(long id);

    /**
     * Обновляет в бд только указанные поля сущности.
     *
     * @param entity сущность
     */
    //@CachePut(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #entity.getId()")
    public abstract T patch(T entity, Set<String> fields);
}