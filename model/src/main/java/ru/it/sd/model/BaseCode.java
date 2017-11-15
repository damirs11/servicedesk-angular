package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Базовый класс для кодов
 *
 * @author quadrix
 * @since 15.11.2017
 */
public class BaseCode  {

	/**
	 * Идентификатор
	 */
	@FieldMeta(columnName = "id", key = true)
	private Long id;

	/**
	 * Название
	 */
	@FieldMeta(columnName = "name")
	private String name;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

	// Возвращает вид кода по типу сущности
	protected Long getTypeId(EntityType entityType) {
		return null;
	}
}
