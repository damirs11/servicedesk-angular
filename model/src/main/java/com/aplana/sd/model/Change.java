package com.aplana.sd.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Изменения"
 *
 * @author quadrix
 * @since 03.05.2017
 */
public class Change implements HasId, HasStatus, Serializable {

	/** Уникальный идентификатор */
	private Long id;
	/** Номер */
	private Long no;
	/** Тема */
	private String subject;
	/** Описание */
	private String description;
	/** Статус */
	private EntityStatus status;
	/** Приоритет */
	private EntityPriority priority;

	/** Дата создания */
	private Date createdDate;
	/** Крайний срок */
	private Date deadline;
	/** Фактически выполнено */
	private Date resolvedDate;

	/** Инициатор изменения */
	private Person initiator;
	/** Менеджер изменения */
	private Person manager;

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
}