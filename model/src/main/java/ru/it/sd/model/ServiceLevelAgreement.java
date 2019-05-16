package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.Date;

/**
 * Модель для SLA
 *
 * @author nsychev
 */
@ClassMeta(tableName = "itsm_service_level_agreements", tableAlias = "sla")
public class ServiceLevelAgreement implements HasId, HasFolder, HasStatus, Serializable {

    private static final long serialVersionUID = 3931138494906330923L;

    public ServiceLevelAgreement() {
    }

    public ServiceLevelAgreement(Long id) {
        this.id = id;
    }

    @FieldMeta(columnName = "sla_oid", key = true)
    private Long id;
    /**
     * Номер
     */
    @FieldMeta(columnName = "sla_id")
    private Long no;

    /**
     * Сервис
     */
    @FieldMeta(columnName = "sla_srv_oid")
    private Service service;

    /**
     * Условие предоставления
     */
    @FieldMeta(columnName = "sla_sel_oid")
    private ServiceLevel serviceLevel;
    @FieldMeta(columnName = "sla_status_cod_oid")
    private EntityStatus status;

    @FieldMeta(columnName = "sla_name")
    private String name;

    @FieldMeta(columnName = "sla_pool_cod_oid")
    private Folder folder;

    @FieldMeta(columnName = "sla_actualstart")
    private Date validFrom;

    @FieldMeta(columnName = "sla_actualfinish")
    private Date validTo;
    @FieldMeta(columnName = "sla_per_oid")
    private Person person;
    @FieldMeta(columnName = "sla_wog_oid")
    private Workgroup workgroup;
    /**
     * Классификация заявок обязательна
     */
    @FieldMeta(columnName = "slc_boolean1", tableAlias = "slc")
    private Boolean classificationRequired;
    /**
     * Трудозатраты обязательны
     */
    @FieldMeta(columnName = "slc_boolean2", tableAlias = "slc")
    private Boolean laborCostsRequired;

    @FieldMeta(columnName = "slc_cod1_oid", tableAlias = "slc")
    private DefaultPriority defaultPriority;

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

    public ServiceLevel getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(ServiceLevel serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Workgroup getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(Workgroup workgroup) {
        this.workgroup = workgroup;
    }

    public Boolean getClassificationRequired() {
        return classificationRequired;
    }

    public void setClassificationRequired(Boolean classificationRequired) {
        this.classificationRequired = classificationRequired;
    }

    public Boolean getLaborCostsRequired() {
        return laborCostsRequired;
    }

    public void setLaborCostsRequired(Boolean laborCostsRequired) {
        this.laborCostsRequired = laborCostsRequired;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public DefaultPriority getDefaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(DefaultPriority defaultPriority) {
        this.defaultPriority = defaultPriority;
    }
}
