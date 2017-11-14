package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Модельный класс для кодов из таблицы rep_codes
 *
 * @author quadrix
 * @since 15.11.2017
 */
@ClassMeta(tableName = "rep_codes", tableAlias = "rcd")
public abstract class AbstractRepCode implements Code, HasId, Serializable {

	/**
	 * Идентификатор
	 */
	@FieldMeta(columnName = "rcd_oid", key = true)
	private Long id;

	/**
	 * Название
	 */
	@FieldMeta(tableAlias = "rct", columnName = "rct_name")
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

	/**
	 * Возвращает тип кода по типу сущности
	 */
	protected abstract Long getTypeId(EntityType entityType);
}
