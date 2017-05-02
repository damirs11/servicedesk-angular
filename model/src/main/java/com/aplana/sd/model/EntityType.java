package com.aplana.sd.model;

/**
 * Типы сущностей, с которыми работает система
 *
 * @author quadrix
 *         01.05.2017 22:17
 */
public enum EntityType {

	CHANGE("Изменение"),
	PROBLEM("Проблема"),
	CALL("Обращение");

	/** Название */
	private String name;

	EntityType(String name) {
		this.name = name;
	}

}