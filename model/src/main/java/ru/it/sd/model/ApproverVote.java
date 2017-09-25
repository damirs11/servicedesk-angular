package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "ApproverVotes"
 *
 * @author NSychev
 * @since 25.09.2017
 */
@ClassMeta(tableName = "itsm_approver_votes", tableAlias = "apv")
public class ApproverVote implements HasId,  Serializable {

	@FieldMeta(columnName = "apv_oid")
	private Long id;

	@FieldMeta(columnName = "apv_apt_oid")
	private Long entity;

	@FieldMeta(columnName = "apv_approved")
	private Integer approved;

	@FieldMeta(columnName = "apv_per_oid")
	private Person approver;

	@FieldMeta(columnName = "apv_reason")
	private String reason;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntity() {
		return entity;
	}

	public void setEntity(Long entity) {
		this.entity = entity;
	}

	public Integer getApproved() {
		return approved;
	}

	public void setApproved(Integer approved) {
		this.approved = approved;
	}

	public Person getApprover() {
		return approver;
	}

	public void setApprover(Person approver) {
		this.approver = approver;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "ApproverVote{" +
				"id=" + id +
				", entity=" + entity +
				", approved=" + approved +
				", approver=" + approver +
				", reason='" + reason + '\'' +
				'}';
	}
}