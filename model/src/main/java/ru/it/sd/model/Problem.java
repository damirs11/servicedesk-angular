package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.util.Date;

/**
 * Модельный класс для "Проблем"
 * @author nsychev
 * @since 05.06.2018
 */
@ClassMeta(tableName = "itsm_problems", tableAlias = "pro")
public class Problem implements HasId, HasStatus, HasFolder, HasAssignment{
    /** ID */
    @FieldMeta(columnName = "pro_oid", key = true)
    private Long id;
    /** Номер */
    @FieldMeta(columnName = "pro_id")
    private Long no;
    /** Статус */
    @FieldMeta(columnName = "pro_sta_oid")
    private EntityStatus status;
    /** Инициатор */
    @FieldMeta(columnName = "pro_requestor_per_oid")
    private Person initiator;
    /** Объект обслуживания */
    @FieldMeta(columnName = "pro_cit_oid")
    private ConfigurationItem configurationItem;
    /** Система */
    //todo EntityCode4
    /** Тема */
    @FieldMeta(columnName = "pro_description")
    private String subject;
    /** Информация */
    @FieldMeta(columnName = "pri_information", tableAlias = "pri")
    private String description;
    /** Ссылки на лог-файлы */
    @FieldMeta(columnName = "pcf_problemtext4", tableAlias = "pcf")
    private String logLinks;
    /** Ссылка в jira */
    @FieldMeta(columnName = "pcf_problemtext1", tableAlias = "pcf")
    private String jiraLink;
    /** Номер/дата обращения к вендору */
    @FieldMeta(columnName = "pcf_problemtext3", tableAlias = "pcf")
    private String toVendor;
    /** Обходное решение */
    @FieldMeta(columnName = "pr2_4k2")
    private String workaround;
    /** Решение */
    @FieldMeta(columnName = "prs_solution")
    private String solution;
    /** Комментарий инициатору */
    @FieldMeta(columnName = "pro_workaround")
    private String commentToInitiator;
    /** Приоритет */
    @FieldMeta(columnName = "pro_imp_oid")
    private EntityPriority priority;
    /** Крайний срок */
    @FieldMeta(columnName = "pro_deadline")
    private Date deadline;
    /** Выполнено(дата) */
    @FieldMeta(columnName = "pro_actualfinish")
    private Date resolvedDate;
    /** Закрыто(дата) */
    @FieldMeta(columnName = "pro_latefinish")
    private Date closureDate;
    /** Просрочена*/
    @FieldMeta(columnName = "pcf_boolean12")
    private Boolean isOverdue;
    /** Кем просрочена*/
    @FieldMeta(columnName = "pcf_proshorttext2")
    private String whoOverdue;
    /** План завершения*/
    @FieldMeta(columnName = "pro_planfinish")
    private Date planFinish;
    /** Причина переноса крайнего срока*/
    @FieldMeta(columnName = "pcf_problemtext2")
    private String deferralReason;
    /** Назначено */
    private Assignment assignment;
    @FieldMeta(columnName = "pro_cat_oid")
    private EntityCategory category;
    @FieldMeta(columnName = "pro_cla_oid")
    private EntityClassification classification;

    //todo функционал
    @FieldMeta(columnName = "pro_clo_oid")
    private EntityClosureCode closureCode;
    /** Папка */
    @FieldMeta(columnName = "pro_poo_oid")
    private Folder folder;
    /** Не включать в отчет Заказчику*/
    @FieldMeta(columnName = "pcf_boolean1")
    private Boolean notAttachInReport;

    //todo проявляется в версии
    //todo решено в версии
    /** Дата выпуска версии*/
    @FieldMeta(columnName = "pcf_problemdate2")
    private Date versionDate;
    /** Комментарий исполнителю*/
    @FieldMeta(columnName = "pr1_4k1")
    private String commentToExecutor;

    @Override
    public Assignment getAssignment() {
        return assignment;
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
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public EntityStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public Person getInitiator() {
        return initiator;
    }

    public void setInitiator(Person initiator) {
        this.initiator = initiator;
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

    public String getLogLinks() {
        return logLinks;
    }

    public void setLogLinks(String logLinks) {
        this.logLinks = logLinks;
    }

    public String getJiraLink() {
        return jiraLink;
    }

    public void setJiraLink(String jiraLink) {
        this.jiraLink = jiraLink;
    }

    public String getToVendor() {
        return toVendor;
    }

    public void setToVendor(String toVendor) {
        this.toVendor = toVendor;
    }

    public String getWorkaround() {
        return workaround;
    }

    public void setWorkaround(String workaround) {
        this.workaround = workaround;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getCommentToInitiator() {
        return commentToInitiator;
    }

    public void setCommentToInitiator(String commentToInitiator) {
        this.commentToInitiator = commentToInitiator;
    }

    public EntityPriority getPriority() {
        return priority;
    }

    public void setPriority(EntityPriority priority) {
        this.priority = priority;
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

    public Boolean getOverdue() {
        return isOverdue;
    }

    public void setOverdue(Boolean overdue) {
        isOverdue = overdue;
    }

    public String getWhoOverdue() {
        return whoOverdue;
    }

    public void setWhoOverdue(String whoOverdue) {
        this.whoOverdue = whoOverdue;
    }

    public Date getPlanFinish() {
        return planFinish;
    }

    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    public String getDeferralReason() {
        return deferralReason;
    }

    public void setDeferralReason(String deferralReason) {
        this.deferralReason = deferralReason;
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

    public Boolean getNotAttachInReport() {
        return notAttachInReport;
    }

    public void setNotAttachInReport(Boolean notAttachInReport) {
        this.notAttachInReport = notAttachInReport;
    }

    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public String getCommentToExecutor() {
        return commentToExecutor;
    }

    public void setCommentToExecutor(String commentToExecutor) {
        this.commentToExecutor = commentToExecutor;
    }
}
