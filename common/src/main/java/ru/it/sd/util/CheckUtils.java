package ru.it.sd.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;

/**
 * Вспомогательные методы для проверки значений
 *
 * @author quadrix
 * @since 30.05.2017 19:54
 */
public class CheckUtils {

	private static final Logger logger = LoggerFactory.getLogger(CheckUtils.class);

	/**
	 * Проверяет, что объект определен
	 *
	 * @param object проверяемый объект
	 * @param error текст ошибки, если проверка не пройдет
	 * @throws NullPointerException если объект не определен
	 */
	public static void requireNotNull(Object object, String error) {
		Objects.requireNonNull(object, error);
	}

	public static void requireNotNull(Object object) {
		requireNotNull(object, "Объект на задан");
	}

	/**
	 * Проверяет, что коллекция определена и не пустая
	 *
	 * @param collection проверяемая коллекция
	 * @param error текст ошибки, если проверка не пройдет
	 * @throws NullPointerException если коллекция не определена
	 * @throws IllegalArgumentException если коллекция пустая
	 */
	public static void requireNotEmpty(Collection collection, String error) {
		requireNotNull(collection, error);
		if (collection.isEmpty()) {
			throw new IllegalArgumentException(error);
		}
	}

	public static void requireNotEmpty(Collection collection) {
		requireNotEmpty(collection, "Список не содержит элементов");
	}

	/**
	 * Проверяет выполнение условия по выражению
	 *
	 * @param expression проверяемое выражение. Оно должно быть истинно, чтобы проверка прошла
	 * @param error текст ошибки, если проверка не пройдет
	 * @throws IllegalArgumentException если проверяемое выражение ложно
	 */
	public static void requireCondition(boolean expression, String error) {
		if (!expression) {
			throw new IllegalArgumentException(error);
		}
	}

	public static void requireCondition(boolean expression) {
		requireCondition(expression, "Условие не выполнено");
	}

	/**
	 * Проверяет, что указанная строка не null и содержит значение
	 */
	public static void requireNotBlank(String str, String error) {
		requireCondition(StringUtils.isNotBlank(str), error);
	}

}