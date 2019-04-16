package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.util.Date;

/**
 * Модель для SLA
 * @author nsychev
 */
@ClassMeta(tableName = "itsm_service_level_agreements", tableAlias = "sla")
public class ServiceLevelAgreement implements HasId, HasFolder, HasStatus {

    @FieldMeta(columnName = "sla_oid", key = true)
    private Long id;

    /**
     * Сервис
     */
    @FieldMeta(columnName = "sla_srv_oid")
    private Service service;

    /**
     * Условие предоставления
     */

    @FieldMeta(columnName = "sla_status_cod_oid")
    private EntityStatus status;
    @FieldMeta(columnName = "sla_sel_oid")
    private Long agreementCondition;

    @FieldMeta(columnName = "sla_name")
    private String name;

    @FieldMeta(columnName = "sla_pool_cod_oid")
    private Folder folder;

    @FieldMeta(columnName = "sla_actualstart")
    private Date validFrom;

    @FieldMeta(columnName = "sla_actualfinish")
    private Date validTo;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Folder getFolder() {
        return folder;
    }

    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Long getAgreementCondition() {
        return agreementCondition;
    }

    public void setAgreementCondition(Long agreementCondition) {
        this.agreementCondition = agreementCondition;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EntityStatus status) {
        this.status = status;
    }
}
