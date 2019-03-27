package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

/**
 * Модель для SLA
 * @author nsychev
 */
@ClassMeta(tableName = "itsm_service_level_agreements", tableAlias = "sla")
public class ServiceLevelAgreement implements HasId, HasFolder {

    @FieldMeta(columnName = "sla_oid", key = true)
    private Long id;

    /**
     * Сервис
     */
    @FieldMeta(columnName = "sla_srv_oid")
    private Long service;

    /**
     * Условие предоставления
     */
    @FieldMeta(columnName = "sla_sel_oid")
    private Long agreementCondition;

    @FieldMeta(columnName = "sla_name")
    private String name;

    @FieldMeta(columnName = "sla_pool_cod_oid")
    private Folder folder;

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

    public Long getService() {
        return service;
    }

    public void setService(Long service) {
        this.service = service;
    }

    public Long getAgreementCondition() {
        return agreementCondition;
    }

    public void setAgreementCondition(Long agreementCondition) {
        this.agreementCondition = agreementCondition;
    }
}
