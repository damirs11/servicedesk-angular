package ru.it.sd.model;

/**
 * Типы сущностей, с которыми работает система
 *
 * @author quadrix
 * @since 01.05.2017
 */
public enum EntityType {

	CHANGE("Изменение"),
	PROBLEM("Проблема"),
	CALL("Обращение"),
	WORKORDER("Наряд");

	/** Название */
	private String name;

	EntityType(String name) {
		this.name = name;
	}

}