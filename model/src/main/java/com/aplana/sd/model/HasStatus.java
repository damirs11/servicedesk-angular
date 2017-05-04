package com.aplana.sd.model;

/**
 * Интерфейс для объектов со статусом
 *
 * @author quadrix
 * @since 02.08.2016
 */

public interface HasStatus {
	/** Получить значение статуса */
	EntityStatus getStatus();
	/** Установить значение статуса */
	void setStatus(EntityStatus id);
}