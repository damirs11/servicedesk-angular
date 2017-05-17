package ru.it.sd.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для описания параметров класса
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassMeta {

	/**
	 * Название таблицы в БД
	 */
	String tableName();

	/**
	 * Название поля для отображения на клиенте, например, в заголовке столбца таблицы
	 */
	String title() default "";

}