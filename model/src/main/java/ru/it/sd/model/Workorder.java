package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Изменения"
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_workorders")
public class Workorder implements HasId, HasStatus, Serializable {

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "OID")
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "description", maxLength = 80)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "woi_information", maxLength = 4000)
	private String description;

	/** Статус */
	private EntityStatus status;
	/** Категория */
	private EntityCategory category;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created")
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "wor_deadline")
	private Date deadline;
	/** Фактически выполнено */
	@FieldMeta(columnName = "wor_actualfinish")
	private Date resolvedDate;

	@FieldMeta(columnName = "scf_duration1")

	/** Инициатор */
	private Person initiator;
	/** Исполнитель */
	private Person doer;

	/** Просрочен */
	@FieldMeta(columnName = "wcf_boolean2")
	private Boolean expired;

	/** Изменение */
	private Change change;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public EntityStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public EntityCategory getCategory() {
		return category;
	}

	public void setCategory(EntityCategory category) {
		this.category = category;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Date resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public Person getInitiator() {
		return initiator;
	}

	public void setInitiator(Person initiator) {
		this.initiator = initiator;
	}

	public Person getDoer() {
		return doer;
	}

	public void setDoer(Person doer) {
		this.doer = doer;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public Change getChange() {
		return change;
	}

	public void setChange(Change change) {
		this.change = change;
	}
}