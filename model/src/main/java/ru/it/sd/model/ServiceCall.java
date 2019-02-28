package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.io.Serializable;
import java.util.Date;

@ClassMeta(tableName = "itsm_servicecalls", tableAlias = "s")
public class ServiceCall implements HasId, HasStatus, HasFolder, HasAssignment, Serializable {
    private static final long serialVersionUID = -4734718359033452525L;

    /**
     * OID
     */
    @FieldMeta(columnName = "ser_oid", key = true)
    private Long id;
    /**
     * Номер
     */
    @FieldMeta(columnName = "ser_id")
    private Long no;
    /**
     * Назначение
     */
    @FieldMeta(columnName = "", attribute = 665649208)
    private Assignment assignment;
    /**
     * Папка
     */
    @FieldMeta(columnName = "ser_poo_oid", attribute = 1032388614)
    private Folder folder;
    /**
     * Статус
     */
    @FieldMeta(columnName = "ser_sta_oid", attribute = 662896684)//281483984437250 status(ServiceCall today)
    private EntityStatus status;
    /**
     * Категория
     */
    @FieldMeta(columnName = "ser_cat_oid")
    private EntityCategory category;
    /**
     * Классификатор
     */
    @FieldMeta(columnName = "ser_cla_oid")
    private EntityClassification classification;
    /**
     * Код завершения
     */
    @FieldMeta(columnName = "ser_clo_oid", attribute = 75251)
    private EntityClosureCode closureCode;
    /**
     * Шаблон
     */
    @FieldMeta(columnName = "ser_tem_oid")
    private Template template;
    /**
     * Решение
     */
    @FieldMeta(columnName = "scs_solution", tableAlias = "scs")
    private String solution;
    /**
     * Инициатор
     */
    @FieldMeta(columnName = "ser_requestor_per_oid")
    private Person initiator;
    /**
     * Заявитель
     */
    @FieldMeta(columnName = "ser_caller_per")
    private Person caller;
    /**
     * Организация заявителя
     */
    @FieldMeta(columnName = "ser_caller_org")
    private Organization organization;
    /**
     * SLA
     */
    @FieldMeta(columnName = "ser_sla_oid")
    private Long agreement;//todo
    /**
     * Сервис/Услуга
     */
    @FieldMeta(columnName = "sla_srv_oid", tableAlias = "sla")
    private Long serviceLevelAgreement;//todo
    /**
     * Условия предоставления
     */
    @FieldMeta(columnName = "sla_sel_oid", tableAlias = "sla")
    private Long agreementCondition;//todo
    /**
     * Ext ID
     */
    @FieldMeta(columnName = "scf_sershorttext10", tableAlias = "scf")
    private String extId;
    /**
     * Объект обслуживания
     */
    @FieldMeta(columnName = "sci_cit_oid", tableAlias = "sci")
    private ConfigurationItem configurationItem;
    /**
     * Тема
     */
    @FieldMeta(columnName = "ser_description")
    private String subject;
    /**
     * Информация
     */
    @FieldMeta(columnName = "sei_information", tableAlias = "sei")
    private String description;
    /**
     * Приоритет
     */
    @FieldMeta(columnName = "ser_imp_oid")
    private EntityPriority priority;
    /**
     * Условия приоритета
     */
    @FieldMeta(columnName = "ser_pri_oid")
    private Long priorityCondition;//todo
    /**
     * Крайний срок
     */
    @FieldMeta(columnName = "ser_deadline")
    private Date deadline;
    /**
     * Выполнено(дата)
     */
    @FieldMeta(columnName = "ser_actualfinish")
    private Date resolvedDate;
    /**
     * Закрыто(дата)
     */
    @FieldMeta(columnName = "ser_latefinish")
    private Date closureDate;
    /**
     * Заявка просрочена
     */
    @FieldMeta(columnName = "scf_boolean1", tableAlias = "scf")
    private Boolean expired;
    /**
     * Кем просрочена
     */
    @FieldMeta(columnName = "scf_sershorttext4", tableAlias = "scf")
    private String expiredBy;
    /**
     * Новый крайний срок
     */
    @FieldMeta(columnName = "scf_scdate1", tableAlias = "scf")
    private Date newDeadline;
    /**
     * Причина переноса крайнего срока
     */
    @FieldMeta(columnName = "scf_sctext11", tableAlias = "scf")
    private String newDeadlineReason;
    /**
     * Руководитель исполнителя
     */
    @FieldMeta(columnName = "scf_per1_oid", tableAlias = "scf")
    private Person executorHead;
    /**
     * Ошибка при обработке заявки
     */
    @FieldMeta(columnName = "scf_boolean10", tableAlias = "scf")
    private Boolean errorHandling;
    /**
     * Функциональная эскалация
     */
    @FieldMeta(columnName = "scf_boolean12", tableAlias = "scf")
    private Boolean functionalEscalation;
    /**
     * Оценка
     */
    @FieldMeta(columnName = "scf_cod1_oid", tableAlias = "scf")
    private EntityCode1 mark;
    /**
     * Комментарий инициатору
     */
    @FieldMeta(columnName = "scw_workaround", tableAlias = "scw")
    private String commentToInitiator;
    /**
     * Комментарий исполнителю
     */
    @FieldMeta(columnName = "se1_4k1", tableAlias = "se1")
    private String commentToExecutor;

    @Override
    public Assignment getAssignment() {
        return assignment;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    @Override
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public Folder getFolder() {
        return folder;
    }

    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public EntityCategory getCategory() {
        return category;
    }

    public void setCategory(EntityCategory category) {
        this.category = category;
    }

    public EntityClassification getClassification() {
        return classification;
    }

    public void setClassification(EntityClassification classification) {
        this.classification = classification;
    }

    public EntityClosureCode getClosureCode() {
        return closureCode;
    }

    public void setClosureCode(EntityClosureCode closureCode) {
        this.closureCode = closureCode;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Person getInitiator() {
        return initiator;
    }

    public void setInitiator(Person initiator) {
        this.initiator = initiator;
    }

    public Person getCaller() {
        return caller;
    }

    public void setCaller(Person caller) {
        this.caller = caller;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Long getAgreement() {
        return agreement;
    }

    public void setAgreement(Long agreement) {
        this.agreement = agreement;
    }

    public Long getServiceLevelAgreement() {
        return serviceLevelAgreement;
    }

    public void setServiceLevelAgreement(Long serviceLevelAgreement) {
        this.serviceLevelAgreement = serviceLevelAgreement;
    }

    public Long getAgreementCondition() {
        return agreementCondition;
    }

    public void setAgreementCondition(Long agreementCondition) {
        this.agreementCondition = agreementCondition;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public ConfigurationItem getConfigurationItem() {
        return configurationItem;
    }

    public void setConfigurationItem(ConfigurationItem configurationItem) {
        this.configurationItem = configurationItem;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntityPriority getPriority() {
        return priority;
    }

    public void setPriority(EntityPriority priority) {
        this.priority = priority;
    }

    public Long getPriorityCondition() {
        return priorityCondition;
    }

    public void setPriorityCondition(Long priorityCondition) {
        this.priorityCondition = priorityCondition;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(Date resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getExpiredBy() {
        return expiredBy;
    }

    public void setExpiredBy(String expiredBy) {
        this.expiredBy = expiredBy;
    }

    public Date getNewDeadline() {
        return newDeadline;
    }

    public void setNewDeadline(Date newDeadline) {
        this.newDeadline = newDeadline;
    }

    public String getNewDeadlineReason() {
        return newDeadlineReason;
    }

    public void setNewDeadlineReason(String newDeadlineReason) {
        this.newDeadlineReason = newDeadlineReason;
    }

    public Person getExecutorHead() {
        return executorHead;
    }

    public void setExecutorHead(Person executorHead) {
        this.executorHead = executorHead;
    }

    public Boolean getErrorHandling() {
        return errorHandling;
    }

    public void setErrorHandling(Boolean errorHandling) {
        this.errorHandling = errorHandling;
    }

    public Boolean getFunctionalEscalation() {
        return functionalEscalation;
    }

    public void setFunctionalEscalation(Boolean functionalEscalation) {
        this.functionalEscalation = functionalEscalation;
    }

    public EntityCode1 getMark() {
        return mark;
    }

    public void setMark(EntityCode1 mark) {
        this.mark = mark;
    }

    public String getCommentToInitiator() {
        return commentToInitiator;
    }

    public void setCommentToInitiator(String commentToInitiator) {
        this.commentToInitiator = commentToInitiator;
    }

    public String getCommentToExecutor() {
        return commentToExecutor;
    }

    public void setCommentToExecutor(String commentToExecutor) {
        this.commentToExecutor = commentToExecutor;
    }
}
