package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Организации, к которым соотносим пользователей системы
 *
 * @author quadrix
 * @since 28.04.2017
 */
@ClassMeta(tableName = "itsm_organizations", tableAlias = "o")
public class Organization implements Code, HasFolder, Serializable {

	public Organization() {
	}

	public Organization(Long id) {
		this.id = id;
	}

	private static final long serialVersionUID = -1146630780979985820L;

	/**
	 * Идентификатор
	 */
	@FieldMeta(columnName = "org_oid", key = true)
	private Long id;
	/**
	 * Название
	 */
	@FieldMeta(columnName = "org_name1")
	private String name;
	/**
	 * Email
	 */
	@FieldMeta(columnName = "org_email")
	private String email;

	@FieldMeta(columnName = "org_poo_oid")
	private Folder folder;

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

	@Override
	public Integer getOrder() {
		return null;
	}

	@Override
	public void setOrder(Integer order) {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

	@Override
	public Folder getFolder() {
		return folder;
	}

	@Override
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
}