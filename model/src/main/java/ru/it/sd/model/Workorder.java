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
	@FieldMeta(columnName = "WOR_OID")
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "WOR_ID")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "WOR_DESCRIPTION", maxLength = 80)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "WOI_INFORMATION", maxLength = 4000)
	private String description;
	/** Трудозатраты */
	@FieldMeta(columnName = "WCF_DURATION1")
	private Double labor;
	/** Решение */
	@FieldMeta(columnName = "WO1_4K1", maxLength = 4000)
	private String solution;

	/** Статус */
	private EntityStatus status;
	/** Категория */
	private EntityCategory category;
	/** Код завершения */
	private EntityClosureCode closureCode;

	/** Дата создания */
	@FieldMeta(columnName = "REG_CREATED")
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "WOR_DEADLINE")
	private Date deadline;
	/** Фактически выполнено */
	@FieldMeta(columnName = "WOR_ACTUALFINISH")
	private Date resolvedDate;
	/** Дата изменения */
	@FieldMeta(columnName = "REG_MODIFIED")
	private Date modifyDate;
	/** Наряд просрочен */
	@FieldMeta(columnName = "WCF_BOOLEAN2")
	private Boolean expired;

	/** Инициатор */
	private Person initiator;
	/** Исполнитель */
	private Person assigneePerson;

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

	public Double getLabor() {
		return labor;
	}

	public void setLabor(Double labor) {
		this.labor = labor;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
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


	public EntityClosureCode getClosureCode() {
		return closureCode;
	}

	public void setClosureCode(EntityClosureCode closureCode) {
		this.closureCode = closureCode;
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

	public Person getAssigneePerson() {
		return assigneePerson;
	}

	public void setAssigneePerson(Person assigneePerson) {
		this.assigneePerson = assigneePerson;
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