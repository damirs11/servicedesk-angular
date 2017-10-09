package ru.it.sd.model;

import org.springframework.security.core.GrantedAuthority;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

/**
 * @author quadrix
 * @since 25.09.2017
 */
@ClassMeta(tableName = "rep_roles")
public class Role implements Code, GrantedAuthority {

	/** Идентификатор роли */
	@FieldMeta(columnName = "rol_oid")
	private Long id;
	/** Наименование роли */
	@FieldMeta(columnName = "rol_description")
	private String name;
	/** Может ли редактировать данные как таковые. Поле используется, например, для аудиторов */
	@FieldMeta(columnName = "rol_updateallallowed")
	private Boolean editor;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEditor() {
		return editor;
	}

	public void setEditor(Boolean editor) {
		this.editor = editor;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + name;
	}
}