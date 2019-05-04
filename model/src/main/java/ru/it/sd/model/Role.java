package ru.it.sd.model;

import org.springframework.security.core.GrantedAuthority;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.util.Objects;

/**
 * @author quadrix
 * @since 25.09.2017
 */
@ClassMeta(tableName = "rep_roles")
public class Role implements Code, GrantedAuthority {

	private static final long serialVersionUID = 3230787447825628308L;
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

	@Override
	public Integer getOrder() {
		return null;
	}

	@Override
	public void setOrder(Integer order) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return Objects.equals(id, role.id) &&
				Objects.equals(name, role.name) &&
				Objects.equals(editor, role.editor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, editor);
	}
}