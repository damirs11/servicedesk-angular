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
    @FieldMeta(columnName = "", attribute = 665649208L)
    private Assignment assignment;
    /**
     * Папка
     */
    @FieldMeta(columnName = "ser_poo_oid", attribute = 1032388614L)
    private Folder folder;
    /**
     * Статус
     */
    @FieldMeta(columnName = "ser_sta_oid", attribute = 662896684L)//281483984437250 status(ServiceCall today)
    private EntityStatus status;
    /**
     * Категория
     */
    @FieldMeta(columnName = "ser_cat_oid", attribute = 681902252L)
    private EntityCategory category;
    /**
     * Классификатор
     */
    @FieldMeta(columnName = "ser_cla_oid", attribute = 1104216100L)
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
    @FieldMeta(columnName = "scs_solution", tableAlias = "scs", attribute = 563019810L)
    private String solution;
    /**
     * Инициатор
     */
    @FieldMeta(columnName = "ser_requestor_per_oid", attribute = 281478292766727L)
    private Person initiator;
    /**
     * Заявитель
     */
    @FieldMeta(columnName = "ser_caller_per", attribute = 70376L)
    private Person caller;
    /**
     * Организация заявителя
     */
    @FieldMeta(columnName = "ser_caller_org", attribute = 70378L)
    private Organization organization;
    /**
     * SLA
     */
    @FieldMeta(columnName = "ser_sla_oid", attribute = 281478535249929L)
    private ServiceLevelAgreement serviceLevelAgreement;
    /**
     * Сервис\услуга
     */
    @FieldMeta(columnName = "ser_srv_oid", attribute = 281478535249927L)
    private Service service;
    /**
     * Ext ID
     */
    @FieldMeta(columnName = "scf_sershorttext10", tableAlias = "scf", attribute = 281481727837054L)//todo check
    private String extId;
    /**
     * Объект обслуживания
     */
    @FieldMeta(columnName = "ser_cit_oid", attribute = 884867074L)
    private ConfigurationItem configurationItem;
    /**
     * Тема
     */
    @FieldMeta(columnName = "ser_description", attribute = 556335107L)
    private String subject;
    /**
     * Информация
     */
    @FieldMeta(columnName = "sei_information", tableAlias = "sei", attribute = 738983997L)
    private String description;
    /**
     * Приоритет
     */
    @FieldMeta(columnName = "ser_imp_oid", attribute = 281478611337217L)
    private EntityPriority priority;
    /**
     * Условия приоритета
     */
    @FieldMeta(columnName = "ser_pri_oid", attribute = 281478616973395L)//todo check
    private Long priorityCondition;
    /**
     * Крайний срок
     */
    @FieldMeta(columnName = "ser_deadline", attribute = 1082392579L)
    private Date deadline;
    /**
     * Выполнено(дата)
     */
    @FieldMeta(columnName = "ser_actualfinish", attribute = 556335112L)
    private Date resolvedDate;
    /**
     * Закрыто(дата)
     */
    @FieldMeta(columnName = "ser_latefinish", attribute = 70370L)
    private Date closureDate;
    /**
     * Заявка просрочена
     */
    @FieldMeta(columnName = "scf_boolean1", tableAlias = "scf", attribute = 281479977042592L)
    private Boolean expired;
    /**
     * Кем просрочена
     */
    @FieldMeta(columnName = "scf_sershorttext4", tableAlias = "scf", attribute = 4067360797L)
    private String expiredBy;
    /**
     * Новый крайний срок
     */
    @FieldMeta(columnName = "scf_scdate1", tableAlias = "scf")//todo
    private Date newDeadline;
    /**
     * Причина переноса крайнего срока
     */
    @FieldMeta(columnName = "scf_sctext11", tableAlias = "scf", attribute = 281481727836884L)
    private String newDeadlineReason;
    /**
     * Руководитель исполнителя
     */
    @FieldMeta(columnName = "scf_per1_oid", tableAlias = "scf", attribute = 281479129333777L)
    private Person executorHead;
    /**
     * Ошибка при обработке заявки
     */
    @FieldMeta(columnName = "scf_boolean10", tableAlias = "scf", attribute = 281478373900379L)//todo check
    private Boolean errorHandling;
    /**
     * Функциональная эскалация
     */
    @FieldMeta(columnName = "scf_boolean12", tableAlias = "scf", attribute = 281478373900387L)
    private Boolean functionalEscalation;
    /**
     * Оценка
     */
    @FieldMeta(columnName = "scf_cod1_oid", tableAlias = "scf", attribute = 281481727837053L)
    private EntityCode1 mark;
    /**
     * Комментарий инициатору
     */
    @FieldMeta(columnName = "scw_workaround", tableAlias = "scw", attribute = 563019807L)
    private String commentToInitiator;
    /**
     * Комментарий исполнителю
     */
    @FieldMeta(columnName = "se1_4k1", tableAlias = "se1", attribute = 281479977894237L)
    private String commentToExecutor;

    @FieldMeta(columnName = "scf_scdate8", tableAlias = "scf")
    private Date renewalDate;
    @FieldMeta(columnName = "scf_sctext5", tableAlias = "scf")
    private String renewalComment;
    @FieldMeta(columnName = "scf_cod7_oid", tableAlias = "scf")
    private EntityCode7 renewalReason;

    /**
     * Поля для получения доступа к вкладкам
     */

    @FieldMeta(columnName = "", attribute = 281478248988673L)
    private long approval;

    @FieldMeta(columnName = "", attribute = 68465L)
    private long attachment;

    @FieldMeta(columnName = "", attribute = 695926812L)
    private long workorders;

    @FieldMeta(columnName = "", attribute = 923336732L)
    private long relations;

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

    public ServiceLevelAgreement getServiceLevelAgreement() {
        return serviceLevelAgreement;
    }

    public void setServiceLevelAgreement(ServiceLevelAgreement serviceLevelAgreement) {
        this.serviceLevelAgreement = serviceLevelAgreement;
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

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public long getApproval() {
        return approval;
    }

    public void setApproval(long approval) {
        this.approval = approval;
    }

    public long getAttachment() {
        return attachment;
    }

    public void setAttachment(long attachment) {
        this.attachment = attachment;
    }

    public long getWorkorders() {
        return workorders;
    }

    public void setWorkorders(long workorders) {
        this.workorders = workorders;
    }

    public long getRelations() {
        return relations;
    }

    public void setRelations(long relations) {
        this.relations = relations;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public String getRenewalComment() {
        return renewalComment;
    }

    public void setRenewalComment(String renewalComment) {
        this.renewalComment = renewalComment;
    }

    public EntityCode7 getRenewalReason() {
        return renewalReason;
    }

    public void setRenewalReason(EntityCode7 renewalReason) {
        this.renewalReason = renewalReason;
    }
}