package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;

/**
 * Модельный класс для "Мнение"
 *
 * @author NSychev
 * @since 25.09.2017
 */
@ClassMeta(tableName = "itsm_approver_votes", tableAlias = "apv")
public class ApproverVote implements HasId,  Serializable {

	private static final long serialVersionUID = 2315838254380437513L;

	@FieldMeta(columnName = "apv_oid")
	private Long id;

	@FieldMeta(columnName = "apv_apt_oid")
	private Long entityId;

	@FieldMeta(columnName = "apv_approved")
	private Boolean approved;

	@FieldMeta(columnName = "apv_per_oid")
	private Person approver;

	@FieldMeta(columnName = "apv_reason")
	private String reason;

	@FieldMeta(columnName = "")
	private EntityType entityType;


	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
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
				", entityId=" + entityId +
				", approved=" + approved +
				", approver=" + approver +
				", reason='" + reason + '\'' +
				'}';
	}
}