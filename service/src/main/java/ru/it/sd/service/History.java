package ru.it.sd.service;

import ru.it.sd.model.EntityHistory;
import ru.it.sd.model.HistoryType;

/**
 * Интерфейс для классов, которые предоставляют информацию об истории сущности <br>
 *
 * EntityClass - модельный класс, для которого получаем историю <br>
 * HistoryEntityClass - класс, который представляем собой запись истории
 *
 * @author mfayzullin@it.ru
 * @since 29.08.2017 15:58
 */
public abstract class History<EntityClass, HistoryEntityClass extends EntityHistory> extends ReadService<HistoryEntityClass>{
	/**
	 * Записывает комментарий в сущность
	 * @param entityId идентификатор сущности
	 * @param message текст комментария
	 */
	public abstract void talkToChat(long entityId, String message, HistoryType historyType);
}
