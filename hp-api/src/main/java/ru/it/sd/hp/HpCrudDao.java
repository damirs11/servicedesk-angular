package ru.it.sd.hp;

import ru.it.sd.model.HasId;

/**
 * Базовый интерфейс для работы с сущностями HP API
 *
 * @author user on 27.07.2017.
 */
public interface HpCrudDao<T extends HasId, K> {

	/**
	 * Создает новый экземпляр hp-сущности.
	 * @param entity обычная сущность, из которой извлекаем значения полей
	 *
	 * @return идентификатор новой сущности
	 */
	long create(T entity);

	/**
	 * Получает hp-сущность по её идентификатору
	 *
	 * @param id идентификатор запрашиваемого объекта
	 * @return hp-сущность
	 */
	K read(long id);

	/**
	 * Обновляет значения hp-сущности.
	 *
	 * @param entity объект из которого получаем значения полей
	 */
	void update(T entity);

	/**
	 * Удаляем hp-сущность по её идентификтору
	 *
	 * @param id идентификатор удаляемой сущности
	 */
	void delete(long id);
}