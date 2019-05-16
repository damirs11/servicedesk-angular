package ru.it.sd.hp;

import ru.it.sd.model.HasId;

import java.util.Set;

/**
 * Базовый интерфейс для работы с сущностями HP API
 *
 * @author user on 27.07.2017.
 */
public interface HpCrudDao<T extends HasId, K> extends HpReadDao<T, K> {

	/**
	 * Создает новый экземпляр hp-сущности.
	 * @param entity обычная сущность, из которой извлекаем значения полей
	 *
	 * @return идентификатор новой сущности
	 */
	long create(T entity);

	/**
	 * Обновляет значения hp-сущности.
	 *
	 * @param entity объект из которого получаем значения полей
	 */
	void update(T entity, Set<String> fields);

	/**
	 * Удаляем hp-сущность по её идентификтору
	 *
	 * @param id идентификатор удаляемой сущности
	 */
	void delete(long id);
}