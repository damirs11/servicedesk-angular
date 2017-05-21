package ru.it.sd.util;

import java.text.MessageFormat;

/**
 * @author quadrix
 * @since 22.05.2017
 */
public class EntityUtils {

	private static final String DEFAULT_PACKAGE_NAME = "ru.it.sd.model";

	/**
	 * Разыменовывает модельный класс по имени
	 *
	 * @param entity название класса
	 * @return класс сущности
	 * @throws IllegalArgumentException в качестве аргумента указан несуществующий класс
	 */
	public static Class<?> getEntityClass(String entity) {
		try {
			if (entity.contains(".")) {
				return Class.forName(entity);
			}
			return Class.forName(DEFAULT_PACKAGE_NAME + '.' + entity);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(MessageFormat.format("There isn''t an entity \"{0}\"", entity));
		}
	}
}
