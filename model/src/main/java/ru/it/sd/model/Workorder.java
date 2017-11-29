package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Нарядов"
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_workorders", tableAlias = "w")
public class Workorder implements HasId, HasStatus, Serializable {

	private static final long serialVersionUID = 9067411334107317799L;

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "wor_oid", key = true)
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "wor_id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "wor_description", required = true, maxLength = 80)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "woi_information", required = true, maxLength = 4000, tableAlias = "winfo")
	private String description;
	/** Трудозатраты */
	@FieldMeta(columnName = "wcf_duration1", tableAlias = "wcustom")
	private Double labor;
	/** Решение */
	@FieldMeta(columnName = "wo1_4k1", maxLength = 4000, tableAlias = "wor4k1")
	private String solution;

	/** Статус */
	@FieldMeta(columnName = "wor_sta_oid", required = true)
	private EntityStatus status;
	/** Категория */
	@FieldMeta(columnName = "wor_cat_oid")
	private EntityCategory category;
	/** Код завершения */
	@FieldMeta(columnName = "wor_clo_oid")
	private EntityClosureCode closureCode;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created")
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "wor_deadline")
	private Date deadline;
	/** Фактически выполнено */
	@FieldMeta(columnName = "wor_actualfinish")
	private Date resolvedDate;
	/** Дата изменения */
	@FieldMeta(columnName = "reg_modified")
	private Date modifyDate;
	/** Наряд просрочен */
	@FieldMeta(columnName = "wcf_boolean2", tableAlias = "wcustom")
	private Boolean expired;

	/** Инициатор */
	@FieldMeta(columnName = "wor_requestor_per_oid")
	private Person initiator;
	/** Исполнитель */
	@FieldMeta(columnName = "ass_per_to_oid", required = true)
	private Person assigneePerson;
    /** Группа исполнителей*/
    @FieldMeta(columnName = "ass_workgroup", required = true)
    private Workgroup assWorkgroup;

	/** Изменение */
	@FieldMeta(columnName = "wor_cha_oid")
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

    public Workgroup getAssWorkgroup() {
        return assWorkgroup;
    }

    public void setAssWorkgroup(Workgroup assWorkgroup) {
        this.assWorkgroup = assWorkgroup;
    }
}