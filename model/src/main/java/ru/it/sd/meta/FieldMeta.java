package ru.it.sd.meta;

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
	 * Максимальное количество символов в строке.
	 */
	int maxLength() default Integer.MAX_VALUE;

	/**
	 * Минимальное количество символов в строке.
	 */
	int minLength() default 0;

	/**
	 * Максимальное значение числа
	 */
	long max() default Long.MAX_VALUE;

	/**
	 * Минимальное значение числа
	 */
	long min() default Long.MIN_VALUE;

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