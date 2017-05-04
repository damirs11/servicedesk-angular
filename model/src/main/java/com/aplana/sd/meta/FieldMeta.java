package com.aplana.sd.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация используется для генерации метаданных для отображения
 * полей класса
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMeta {

	/**
	 * Название поля в таблице БД
	 */
	String columnName();

	/**
	 * Название поля для отображения на клиенте, например, в заголовке столбца таблицы
	 */
	String title() default "";

	/**
	 * Ширина столбца таблицы по умолчанию
	 */
	int width() default 100;

	/**
	 * Порядковый номер столбца в таблице по умолчанию
	 */
	int order() default 0;

	/**
	 * Видимое или невидимое поле
	 */
	boolean visible() default true;

	/**
	 * Формат поля. Например, для дат сможем задавать отображение либо как обычно "31.12.2016", либо "декабрь 2016"
	 * и т.д. Для каждого типа поля может потребоваться свой формат. Для чисел, это может быть денежный формат -
	 * "1 000р" и т.д;
	 */
	String format() default "";

	/**
	 * Точность, количество знаков после запятой, не более 19. Тип "Целое число". Обязательный атрибут для чисел.
	 * Используется для редактирования и отображения в таблицах;
	 */
	int precision() default 0;

	/**
	 * Максимальное количество символов в строке.
	 */
	int maxLength() default Integer.MAX_VALUE;

	/**
	 * Минимальное количество символов в строке.
	 */
	int minLength() default 0;

	/**
	 * Минимальное значение числа
	 */
	long min() default Long.MIN_VALUE;

	/**
	 * Максимальное значение числа
	 */
	long max() default Long.MAX_VALUE;

	/**
	 * Является ли поле обязательным при заполнении.
	 */
	boolean required() default false;

	/**
	 * Поле только для чтения.
	 */
	boolean readOnly() default false;

	/**
	 * Признак уникальности поля. 0 - неуникальное. Несколько полей могут иметь
	 * одно и тоже значение для поля unique - таким образом, можно задавать
	 * группы уникальности.
	 */
	int unique() default 0;

	/**
	 * Regex выражение для проверки значения поля на клиенте
	 */
	String pattern() default "";
}