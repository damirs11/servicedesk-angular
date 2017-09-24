package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

/**
 * Описывает параметры доступа роли на указанный тип сущности.
 *
 * @author quadrix
 * @since 25.09.2017
 */
@ClassMeta(tableName = "rep_entity_access", tableAlias ="a")
public class EntityAccess {

	@FieldMeta(columnName = "ena_oid")
	private Long id;
	@FieldMeta(columnName = "ena_status_from_oid")
	private EntityStatus fromStatus;
	@FieldMeta(columnName = "ena_status_to_oid")
	private EntityStatus toStatus;
	@FieldMeta(columnName = "ena_rol_oid")
	private UserRole role;
	@FieldMeta(columnName = "ena_ent_oid")
	private EntityType entityType;

	@FieldMeta(columnName = "ena_new")
	private Boolean createNew;
	@FieldMeta(columnName = "ena_view")
	private Boolean view;
	@FieldMeta(columnName = "ena_modify")
	private Boolean modify;
	@FieldMeta(columnName = "ena_delete")
	private Boolean delete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntityStatus getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(EntityStatus fromStatus) {
		this.fromStatus = fromStatus;
	}

	public EntityStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(EntityStatus toStatus) {
		this.toStatus = toStatus;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public Boolean getCreateNew() {
		return createNew;
	}

	public void setCreateNew(Boolean createNew) {
		this.createNew = createNew;
	}

	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	public Boolean getModify() {
		return modify;
	}

	public void setModify(Boolean modify) {
		this.modify = modify;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}
}
