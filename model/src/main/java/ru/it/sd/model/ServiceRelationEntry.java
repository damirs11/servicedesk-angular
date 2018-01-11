package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;

/**
 * Модельный класс для "Взаимосвязей"
 *
 * @author NSychev
 * @since 25.12.2017
 */
@ClassMeta(tableName = "itsm_service_relations", tableAlias ="sre")
public class ServiceRelationEntry implements HasId, Serializable {

	private static final long serialVersionUID = -857993162919153346L;

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "sre_oid", key = true)
	private Long id ;

	@FieldMeta(columnName = "sre_revrty_oid")
	private ServiceRelationType type;

    @FieldMeta(columnName = "sre_ent_oid")
    private EntityType entityType;

    @FieldMeta(columnName = "sre_cha_oid")
    private Change change;

    //todo не реализованы инциденты
    @FieldMeta(columnName = "sre_inc_oid")
    private long servicecall;

    //todo не реализованы проблемы
    @FieldMeta(columnName = "sre_pro_oid")
    private long problem;

    //todo не реализованы заявки
    @FieldMeta(columnName = "sre_ser_oid")
    private long incident;

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {

    }

    public ServiceRelationType getType() {
        return type;
    }

    public void setType(ServiceRelationType type) {
        this.type = type;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Change getChange() {
        return change;
    }

    public void setChange(Change change) {
        this.change = change;
    }

    public long getServicecall() {
        return servicecall;
    }

    public void setServicecall(long servicecall) {
        this.servicecall = servicecall;
    }

    public long getProblem() {
        return problem;
    }

    public void setProblem(long problem) {
        this.problem = problem;
    }

    public long getIncident() {
        return incident;
    }

    public void setIncident(long incident) {
        this.incident = incident;
    }
}