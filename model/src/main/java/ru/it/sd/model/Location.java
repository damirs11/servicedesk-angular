package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Местоположение, город.
 * Created by MYXOMOPX on 013 13.06.17.
 */
@ClassMeta(tableName = "itsm_locations")
public class Location implements Code, Serializable {
	/**
	 * Идентификатор
	 */
	@FieldMeta(columnName = "loc_oid", key = true)
	private Long id;
	/**
	 * Название
	 */
	@FieldMeta(columnName = "loc_searchcode")
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
}
