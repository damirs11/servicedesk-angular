package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
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

public class Approval implements HasId, HasStatus, Serializable {

    /*id сущности*/
    @FieldMeta(columnName = "id")
    private Long id;

    /* Статус согласования*/
    @FieldMeta(columnName = "apt_status")
    private EntityStatus status;

    /*Тема*/
    @FieldMeta(columnName = "apt_description")
    private String approvalDescription;


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

    public String getApprovalDescription() {
        return approvalDescription;
    }

    public void setApprovalDescription(String approvalDescription) {
        this.approvalDescription = approvalDescription;
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

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

}