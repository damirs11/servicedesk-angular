package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;

/**
 * Описывает права доступа роли на совершение операций над экземплярами
 * сущности
 *
 * @author quadrix
 * @since 10.10.2017
 */
@ClassMeta(tableName = "rep_entity_access")
public class Grant implements HasId, Serializable {

	/** Идентификатор */
	@FieldMeta(columnName = "ena_oid", key = true)
	private Long id;
	/** Роль */
	@FieldMeta(columnName = "ena_rol_oid")
	private Role role;
	/** Тип сущности, на которую применяются права */
	@FieldMeta(columnName = "ena_ent_oid")
	private EntityType entityType;

	/** Создание нового */
	@FieldMeta(columnName = "ena_new")
	private GrantRule create;
	/** Просмотр */
	@FieldMeta(columnName = "ena_view") //ena_viewasnuser, ena_viewasnwg
	private GrantRule read;
	/** Редактирование */
	@FieldMeta(columnName = "ena_modify") //ena_modifyasnuser, ena_modifyasnwg
	private GrantRule update;
	/** Удаление */
	@FieldMeta(columnName = "ena_delete")
	private GrantRule delete;
	/** Статус, начиная с которого можно редактировать экземпляр, но только, если
	 * выставлено разрешение в поле {@link #update} */
	@FieldMeta(columnName = "ena_status_from_oid")
	private EntityStatus statusFrom;
	/** Статус, по который можно редактировать экземпляр, но только, если
	 * выставлено разрешение в поле {@link #update} */
	@FieldMeta(columnName = "ena_status_to_oid")
	private EntityStatus statusTo;

	/** Создание записи в истории */
	@FieldMeta(columnName = "ena_historynew")
	private GrantRule historyCreate;
	/** Просмотр истории */
	@FieldMeta(columnName = "ena_historyview")
	private GrantRule historyRead;
	/** Редактирование истории */
	@FieldMeta(columnName = "ena_historymodify") //ena_historymodifycreateduser, ena_historymodifycreatedwg
	private GrantRule historyUpdate;
	/** Удаление записи из истории */
	@FieldMeta(columnName = "ena_historydelete") //ena_historydeletecreateduser, ena_historydeletecreatedwg
	private GrantRule historyDelete;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public GrantRule getCreate() {
		return create;
	}

	public void setCreate(GrantRule create) {
		this.create = create;
	}

	public GrantRule getRead() {
		return read;
	}

	public void setRead(GrantRule read) {
		this.read = read;
	}

	public GrantRule getUpdate() {
		return update;
	}

	public void setUpdate(GrantRule update) {
		this.update = update;
	}

	public GrantRule getDelete() {
		return delete;
	}

	public void setDelete(GrantRule delete) {
		this.delete = delete;
	}

	public EntityStatus getStatusFrom() {
		return statusFrom;
	}

	public void setStatusFrom(EntityStatus statusFrom) {
		this.statusFrom = statusFrom;
	}

	public EntityStatus getStatusTo() {
		return statusTo;
	}

	public void setStatusTo(EntityStatus statusTo) {
		this.statusTo = statusTo;
	}

	public GrantRule getHistoryCreate() {
		return historyCreate;
	}

	public void setHistoryCreate(GrantRule historyCreate) {
		this.historyCreate = historyCreate;
	}

	public GrantRule getHistoryRead() {
		return historyRead;
	}

	public void setHistoryRead(GrantRule historyRead) {
		this.historyRead = historyRead;
	}

	public GrantRule getHistoryUpdate() {
		return historyUpdate;
	}

	public void setHistoryUpdate(GrantRule historyUpdate) {
		this.historyUpdate = historyUpdate;
	}

	public GrantRule getHistoryDelete() {
		return historyDelete;
	}

	public void setHistoryDelete(GrantRule historyDelete) {
		this.historyDelete = historyDelete;
	}
}