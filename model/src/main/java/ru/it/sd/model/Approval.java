package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Согласование"
 *
 * @author nsychev
 * @since 03.10.2017
 */
@ClassMeta(tableName = "")
public class Approval implements HasId, HasStatus, Serializable {

    private static final long serialVersionUID = 1163131561854180288L;

    /*id сущности*/
    @FieldMeta(columnName = "id")
    private Long id;

    /*Инициатор согласования*/
    @FieldMeta(columnName = "initiator")
    private Person initiator;

    /* Статус согласования*/
    @FieldMeta(columnName = "apt_status")
    private EntityStatus status;

    /*Тема*/
    @FieldMeta(columnName = "apt_description")
    private String description;


    @FieldMeta(columnName = "deadline")
    private Date deadline;

    /*Необходимое число согласных*/
    @FieldMeta(columnName = "nrofapproversrequired")
	private Integer numberOfApproversRequired;

    /* Число согласующих*/
    @FieldMeta(columnName = "nrofapprovers")
    private Integer numberOfApprovers;

    /* Число одобривших(сколько есть одобрений)*/
    @FieldMeta(columnName = "nrofapproversapproved")
    private Integer numberOfApproversApproved;

    /* Группа согласования*/
    @FieldMeta(columnName = "wog_oid")
    private Workgroup approvalWorkgroup;

    @FieldMeta(columnName = "entityType", required = true)
    private EntityType entityType;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

    @Override
    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EntityStatus id) {
        this.status = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfApproversRequired() {
        return numberOfApproversRequired;
    }

    public void setNumberOfApproversRequired(Integer numberOfApproversRequired) {
        this.numberOfApproversRequired = numberOfApproversRequired;
    }

    public Integer getNumberOfApprovers() {
        return numberOfApprovers;
    }

    public void setNumberOfApprovers(Integer numberOfApprovers) {
        this.numberOfApprovers = numberOfApprovers;
    }

    public Integer getNumberOfApproversApproved() {
        return numberOfApproversApproved;
    }

    public void setNumberOfApproversApproved(Integer numberOfApproversApproved) {
        this.numberOfApproversApproved = numberOfApproversApproved;
    }

    public Workgroup getApprovalWorkgroup() {
        return approvalWorkgroup;
    }

    public void setApprovalWorkgroup(Workgroup approvalWorkgroup) {
        this.approvalWorkgroup = approvalWorkgroup;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Person getInitiator() {
        return initiator;
    }

    public void setInitiator(Person initiator) {
        this.initiator = initiator;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

}