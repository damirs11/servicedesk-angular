package ru.it.sd.service;

import ru.it.sd.model.HasId;

import java.util.List;
import java.util.Map;

/**
 * Универсальный сервис предоставляющий операции чтения данных
 * Read - List
 *
 * @author quadrix
 * @since 22.05.2017
 */
public interface ReadService<T extends HasId> {

    /**
     * Читает сущность из БД по ее идентификатору
     *
     * @param id идентификатор сущности
     * @return объект сущности
     */
    T read(long id);

    /**
     * Возвращает отфильтрованный список сущностей
     *
     * @param filter параметры фильтрации списка
     * @return список сущностей
     */
    List<T> list(Map<String, String> filter);

    /**
     * Возвращает общее количество записей без учета пейджинга.
     * Функция предназначена для постраничного просмотра записей, где помимо
     * данных для текущей страницы необходимо знать сколько страниц еще осталось.
     *
     * @param filter параметры фильтрации
     * @return количество записей.
     */
    int count(Map<String, String> filter);
}