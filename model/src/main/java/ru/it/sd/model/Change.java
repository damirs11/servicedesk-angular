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
public class Change implements Entity, Serializable {

	private static final long serialVersionUID = -857993162919153346L;

	/**
	 * Атрибуты, в которых фиксируется общение пользователей.
	 * Например: workaround и кастомный атрибут
	 */
	public static Long[] chatAttributes = new Long[]{
			HistoryType.CHANGE_INITIATOR.fieldId,
			HistoryType.CHANGE_MANAGER.fieldId
	};

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "cha_oid", key = true)
	private Long id ;
	/** Номер */
	@FieldMeta(columnName = "cha_id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "cha_description", required = true, attribute = 556335107L)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "chi_information", tableAlias = "ci", required = true, attribute = 738983997L)
	private String description;
	/** Статус */
	@FieldMeta(columnName = "cha_sta_oid", attribute = 724041782L)
	private EntityStatus status;
	/** Приоритет */
	@FieldMeta(columnName = "cha_imp_oid", required = true, attribute = 281478611337217L)
	private EntityPriority priority ;

	/** Категория*/
	@FieldMeta(columnName = "cha_cat_oid", required = true, attribute = 724041784L)
	private EntityCategory category;

    /** Классификация*/
    @FieldMeta(columnName = "cha_cla_oid", required = true, attribute = 165888L)
	private EntityClassification classification;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created")
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "cha_deadline", required = true)
	private Date deadline;
	/** Выполнено(дата)*/
	@FieldMeta(columnName = "cha_actualfinish", attribute = 556335112)
	private Date resolvedDate;
    //todo Закрыто(дата) attribut = 70370

	/** Исполнитель */
	@FieldMeta(columnName = "ass_per_to_oid", required = true)
	private Person executor;
	/** Группа исполнителей*/
	@FieldMeta(columnName = "ass_wog_oid", required = true)
	private Workgroup workgroup;

	/** Инициатор изменения */
	@FieldMeta(columnName = "cha_requestor_per_oid", required = true, attribute = 281478292766727L)
	private Person initiator;
	/** Менеджер изменения */
	@FieldMeta(columnName = "cha_per_man_oid", required = true, attribute = 281483590631438L)
	private Person manager;

	@FieldMeta(columnName = "cha_closurecode")
	private EntityClosureCode closureCode;

	@FieldMeta(columnName = "cha_poo_oid")
	private Folder folder;

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
    @Override
	public Person getExecutor() {
		return executor;
	}
    @Override
	public void setExecutor(Person executor) {
		this.executor = executor;
	}

    public EntityCategory getCategory() { return category; }

    public void setCategory(EntityCategory category) { this.category = category; }

    public EntityClassification getClassification() { return classification; }

    public void setClassification(EntityClassification classification) { this.classification = classification; }
    @Override
    public Workgroup getWorkgroup() { return workgroup; }
    @Override
    public void setWorkgroup(Workgroup assWorkgroup) { this.workgroup = assWorkgroup; }

    public EntityClosureCode getClosureCode() {
        return closureCode;
    }

    public void setClosureCode(EntityClosureCode closureCode) {
        this.closureCode = closureCode;
    }
    @Override
    public Folder getFolder() {
        return folder;
    }
    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

}