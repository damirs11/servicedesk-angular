package com.aplana.sd.model;

/**
 * Интерфейс для объектов с идентификаторами
 *
 * @author quadrix
 * @since 02.08.2016
 */

public interface HasId {
	/** Получить значение уникального идентификатора */
	Long getId();
	/** Установить значение уникального идентификатора */
	void setId(Long id);
}