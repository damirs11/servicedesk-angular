package ru.it.sd.service;

import org.springframework.cache.annotation.CacheEvict;
import ru.it.sd.model.HasId;

import java.util.Set;

/**
 * Универсальный сервис предоставляющий оcновные операции
 * Create - Read - Update - Delete - List для всех сущностей.
 *
 * @author quadrix
 * @since 22.05.2017
 */
public interface CrudService<T extends HasId> extends ReadService<T> {

    /**
     * Создает объект сущности.
     *
     * @param entity сущность
     * @return идентификатор созданной сущности
     */
    @CacheEvict(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #id")
    T create(T entity);

    /**
     * Обновляет все поля объекта сущности в БД. Поиск объекта производится по полю id.
     *
     * @param entity сущность
     */
    @CacheEvict(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #id")
    T update(T entity);

    /**
     * Удаляет сущность из БД.
     *
     * @param id        идентификатор сущности
     */
    @CacheEvict(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #id")
    void delete(long id);

    /**
     * Обновляет в бд только указанные поля сущности.
     *
     * @param entity сущность
     */
    @CacheEvict(cacheNames = "entity", key = "#root.targetClass.getSimpleName() + #id")
    T patch(T entity, Set<String> fields);
}