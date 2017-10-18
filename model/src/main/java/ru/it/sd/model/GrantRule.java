package ru.it.sd.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Тип ограничения на право доступа
 *
 * @author quadrix
 * @since 10.10.2017
 */
//todo добавить сериализацию в JSON
public enum GrantRule {

	/** Действие запрещено (не указано) */
	NONE,
	/** Правило действует на все экземпляры сущности */
	ALWAYS,
	/** Правило действует только на экземпляры, где пользователь указан как исполнитель */
	EXECUTOR,
	/** Правило действует только на экземпляры, где указана группа пользователя в качестве исполнителя*/
	WORKGROUP;

	/**
	 * Возвращает правило в зависимости от указанных параметров. В БД данные флаги
	 * хранятся раздельно. Данный метод производит анализ их значений.
	 */
	public static GrantRule get(Boolean always, Boolean executor, Boolean workgroup) {
		if (executor) {
			return EXECUTOR;
		}
		if (workgroup) {
			return WORKGROUP;
		}
		if (always) {
			return ALWAYS;
		}
		return NONE;
	}

}