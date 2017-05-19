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
	@FieldMeta(columnName = "wor_oid")
	private Long id;
	/** Номер */
	@FieldMeta(columnName = "wor_id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "wor_description")
	private String subject;
	/** Описание */
	private String description;
	/** Статус */
	private EntityStatus status;
	/** Приоритет */
	private EntityPriority priority;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created")
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "wor_deadline")
	private Date deadline;
	/** Фактически выполнено */
	@FieldMeta(columnName = "wor_actualfinish")
	private Date resolvedDate;

	/** Инициатор изменения */
	private Person initiator;
	/** Менеджер изменения */
	private Person manager;

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public void setId(Long id) {

	}

	@Override
	public EntityStatus getStatus() {
		return null;
	}

	@Override
	public void setStatus(EntityStatus id) {

	}
}