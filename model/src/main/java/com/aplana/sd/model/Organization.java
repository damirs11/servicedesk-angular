package com.aplana.sd.model;

import com.aplana.sd.meta.ClassMeta;
import com.aplana.sd.meta.FieldMeta;
import com.aplana.sd.util.AppToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Организации, к которым соотносим пользователей системы
 *
 * @author quadrix
 * @since 28.04.2017
 */
@ClassMeta(tableName = "itsm_organizations")
public class Organization implements Code {
	/** Идентификатор */
	@FieldMeta(columnName = "org_oid")
	private Long id;
	/** Название */
	@FieldMeta(columnName = "org_name1")
	private String name;
	/** Email */
	@FieldMeta(columnName = "org_email")
	private String email;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, AppToStringStyle.getInstance())
				.append("id", this.id)
				.append("name", this.name)
				.append("email", this.email)
				.toString();
	}
}