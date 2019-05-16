package ru.it.sd.hp;

import ru.it.sd.model.HasId;

/**
 * Базовый интерфейс для чтение сущности HP API
 *
 * @author nsychev
 */
public interface HpReadDao<T extends HasId, K> {

	/**
	 * Получает hp-сущность по её идентификатору
	 *
	 * @param id идентификатор запрашиваемого объекта
	 * @return hp-сущность
	 */
	K read(long id);

}