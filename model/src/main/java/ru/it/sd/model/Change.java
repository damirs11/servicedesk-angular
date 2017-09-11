package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Изменения"
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_changes", tableAlias ="ch")
public class Change implements HasId, HasStatus, Serializable {

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "cha_oid", key = true)
	private Long id ;
	/** Номер */
	@FieldMeta(columnName = "cha_id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "cha_description", required = true)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "chi_information", tableAlias = "ci", required = true)
	private String description;
	/** Статус */
	@FieldMeta(columnName = "cha_sta_oid")
	private EntityStatus status;
	/** Приоритет */
	@FieldMeta(columnName = "cha_imp_oid", required = true)
	private EntityPriority priority ;

	/** Категория*/
	@FieldMeta(columnName = "cha_cat_oid", required = true)
	private EntityCategory category;

    /** Классификация*/
    @FieldMeta(columnName = "cha_cla_oid", required = true)
	private EntityClassification classification;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created")
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "cha_deadline", required = true)
	private Date deadline;
	/** Фактически выполнено */
	@FieldMeta(columnName = "cha_actualfinish")
	private Date resolvedDate;

	/** Исполнитель */
	@FieldMeta(columnName = "ass_per_to_oid", required = true)
	private Person executor;

	/** Группа исполнителей*/
	@FieldMeta(columnName = "ass_wog_oid", required = true)
	private Workgroup assWorkgroup;

	/** Инициатор изменения */
	@FieldMeta(columnName = "cha_requestor_per_oid", required = true)
	private Person initiator;
	/** Менеджер изменения */
	@FieldMeta(columnName = "cha_per_man_oid", required = true)
	private Person manager;

	// Согласование----------------------------------------------------------------------------------------------

    //* Статус согласования*/
    @FieldMeta(columnName = "cha_apt_status")
    private EntityStatus approvedStatus;

    @FieldMeta(columnName = "cha_apt_description")
    private String approvedDescription;

    /** Требуемое число одобривших(сколько надо одобрений)*/
	@FieldMeta(columnName = "cha_apt_nrofapproversrequired")
	private Integer numberOfApproversRequired;

    /** Число одобряющих*/
    @FieldMeta(columnName = "cha_apt_nrofapprovers")
    private Integer numberOfApprovers;

    /** Число одобривших(сколько есть одобрений)*/
    @FieldMeta(columnName = "cha_apt_nrofapproversapproved")
    private Integer numberOfApproversApproved;

    //** Группа согласования*/
    @FieldMeta(columnName = "cha_apt_wog_oid")
    private Workgroup approvedWorkgroup;

    //* Согласющие ??????????????*/
    @FieldMeta(columnName = "apv_per_oid", tableAlias = "apv")
    private Person[] approvers;
    // ----------------------------------------------------------------------------------------------------------

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

	@Override
	public EntityStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public EntityPriority getPriority() {
		return priority;
	}

	public void setPriority(EntityPriority priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Person getManager() {
		return manager;
	}

	public void setManager(Person manager) {
		this.manager = manager;
	}

	public Person getExecutor() {
		return executor;
	}

	public void setExecutor(Person executor) {
		this.executor = executor;
	}

    public EntityCategory getCategory() { return category; }

    public void setCategory(EntityCategory category) { this.category = category; }

    public EntityClassification getClassification() { return classification; }

    public void setClassification(EntityClassification classification) { this.classification = classification; }

    public Workgroup getAssWorkgroup() { return assWorkgroup; }

    public void setAssWorkgroup(Workgroup assWorkgroup) { this.assWorkgroup = assWorkgroup; }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}
}