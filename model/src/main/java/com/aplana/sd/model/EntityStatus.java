package com.aplana.sd.model;

import static com.aplana.sd.model.EntityType.*;

/**
 * Статусы сущностей
 *
 * @author quadrix
 *         01.05.2017 21:54
 */
public enum EntityStatus {

	CHANGE_REGISTERED("Зарегистрировано", CHANGE),
	CHANGE_PREPARING("Подготовка", CHANGE),
	CHANGE_ON_APPROVE("На согласовании", CHANGE),
	CHANGE_APPROVE_COMPLETE("Согласование завершено", CHANGE),
	CHANGE_EXECUTING("Реализация", CHANGE),
	CHANGE_RESOLVED("Решено", CHANGE),
	CHANGE_CLOSED("Закрыто", CHANGE);

	/** Название статуса */
	private String name;
	/** Тип сущности, к которой относится статус */
	private EntityType entityType;

	EntityStatus(String name, EntityType entityType) {
		this.name = name;
		this.entityType = entityType;
	}
}
