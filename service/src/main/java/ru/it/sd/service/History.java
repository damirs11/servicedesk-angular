package ru.it.sd.service;

import ru.it.sd.model.HasId;

/**
 * Интерфейс для классов, которые предоставляют информацию об истории сущности <br>
 *
 * EntityClass - модельный класс, для которого получаем историю <br>
 * HistoryEntityClass - класс, который представляем собой запись истории
 *
 * @author mfayzullin@it.ru
 * @since 29.08.2017 15:58
 */
public abstract class History<EntityClass, HistoryEntityClass extends HasId> extends ReadService<HistoryEntityClass>{}
