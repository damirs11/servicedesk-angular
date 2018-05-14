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
public class Workorder implements HasId, HasStatus, HasAssignment, HasFolder, Serializable {

	private static final long serialVersionUID = 9067411334107317799L;

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "wor_oid", key = true)
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "wor_id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "wor_description", attribute = 556335107L)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "woi_information", tableAlias = "winfo", attribute = 738983997L)
	private String description;
	/** Трудозатраты */
	@FieldMeta(columnName = "wcf_duration1", tableAlias = "wcustom")
	private Double labor;
	/** Решение */
	@FieldMeta(columnName = "wo1_4k1", tableAlias = "wor4k1", attribute = 281479977894277L)
	private String solution;

	/** Статус */
	@FieldMeta(columnName = "wor_sta_oid", attribute = 854589449L)
	private EntityStatus status;
	/** Категория */
	@FieldMeta(columnName = "wor_cat_oid", attribute = 878116895L)
	private EntityCategory category;
	/** Код завершения */
	@FieldMeta(columnName = "wor_clo_oid", attribute = 75253L)
	private EntityClosureCode closureCode;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created", attribute = 555679750L)
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "wor_deadline", attribute = 556335111L)
	private Date deadline;
	/** Выполнено(Дата)*/
	@FieldMeta(columnName = "wor_actualfinish", attribute = 1082392624L)
	private Date resolvedDate;
    /** Закрыто(Дата)*/
    @FieldMeta(columnName = "wor_latefinish", attribute = 70370L)
    private Date closureDate;
	/** Дата изменения */
	@FieldMeta(columnName = "reg_modified", attribute = 555679750L)
	private Date modifyDate;
	/** Наряд просрочен */
	@FieldMeta(columnName = "wcf_boolean2", tableAlias = "wcustom", attribute = 281479977042622L)
	private Boolean expired;
    /** Кем просрочено */
    @FieldMeta(columnName = "wcf_worshorttext3", tableAlias = "wcustom", attribute = 4067360801L)
    private String expiredBy;
    /** Организация*/
    @FieldMeta(columnName = "wcf_org1_oid", tableAlias = "wcustom", attribute = 281479132938284L)
    private Organization organization;

	@FieldMeta(columnName = "wor_poo_oid", attribute = 1032388614L)
	private Folder folder;
	/** Инициатор */
	@FieldMeta(columnName = "wor_requestor_per_oid", attribute = 281478256590859L)
	private Person initiator;

	/** Изменение */
	@FieldMeta(columnName = "wor_cha_oid", attribute = 724041792L)
	private Change change;

	@FieldMeta(columnName = "", attribute = 665649208)
	private Assignment assignment;

    @FieldMeta(columnName = "", attribute = 68465L)
    private long attachment;

	@FieldMeta(columnName = "wcf_workordertext1", tableAlias = "wcustom", attribute = 1082392634L)
    private String commentToExecutor;

    @FieldMeta(columnName = "wcf_workordertext2", tableAlias = "wcustom", attribute = 1082392635L)
    private String commentToInitiator;

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


    public String getExpiredBy() {
        return expiredBy;
    }

    public void setExpiredBy(String expiredBy) {
        this.expiredBy = expiredBy;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getCommentToExecutor() {
        return commentToExecutor;
    }

    public void setCommentToExecutor(String commentToExecutor) {
        this.commentToExecutor = commentToExecutor;
    }

    public long getAttachment() {
        return attachment;
    }

    public void setAttachment(long attachment) {
        this.attachment = attachment;
    }

    public String getCommentToInitiator() {
        return commentToInitiator;
    }

    public void setCommentToInitiator(String commentToInitiator) {
        this.commentToInitiator = commentToInitiator;
    }

    @Override
	public Assignment getAssignment() {
		return assignment;
	}

	@Override
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
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