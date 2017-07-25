package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

/**
 * Папка доступа
 * Created by MYXOMOPX on 013 13.06.17.
 */
@ClassMeta(tableName = "rep_codes_text")
public class Folder implements Code {
	/**
	 * Идентификатор
	 */
	@FieldMeta(columnName = "rct_rcd_oid", key = true)
	private Long id;
	/**
	 * Название
	 */
	@FieldMeta(columnName = "rct_name")
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
