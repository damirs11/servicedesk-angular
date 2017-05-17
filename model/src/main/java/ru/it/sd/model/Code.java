package ru.it.sd.model;

/**
 * Интерфейс для объектов специального типа "Идентификатор-Название"
 *
 * @author quadrix
 * @since 03.05.2017
 */

public interface Code extends HasId {
	/** Получить наименование объекта */
	String getName();
	/** Установить наименование объекта */
	void setName(String name);
}