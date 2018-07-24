package ru.it.sd.model;

import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;

import java.util.Date;

/**
 * ��������� ����� ��� "�������"
 * @author nsychev
 * @since 05.06.2018
 */
@ClassMeta(tableName = "itsm_problems", tableAlias = "pro")
public class Problem implements HasId, HasStatus, HasFolder, HasAssignment{
    /** ID */
    @FieldMeta(columnName = "pro_oid")
    private Long id;
    /** ����� */
    @FieldMeta(columnName = "pro_id")
    private Long no;
    /** ������ */
    @FieldMeta(columnName = "pro_sta_oid")
    private EntityStatus status;
    /** ��������� */
    @FieldMeta(columnName = "pro_requestor_per_oid")
    private Person initiator;
    /** ������ ������������ */
    @FieldMeta(columnName = "pro_cit_oid")
    private ConfigurationItem configurationItem;
    /** ������� */
    //todo EntityCode4
    /** ���� */
    @FieldMeta(columnName = "pro_description")
    private String subject;
    /** ���������� */
    @FieldMeta(columnName = "pri_information")
    private String description;
    /** ������ �� ���-����� */
    @FieldMeta(columnName = "pcf_problemtext4")
    private String logLinks;
    /** ������ � jira */
    @FieldMeta(columnName = "pcf_problemtext1")
    private String jiraLink;
    /** �����/���� ��������� � ������� */
    @FieldMeta(columnName = "pcf_problemtext3")
    private String toVendor;
    /** �������� ������� */
    @FieldMeta(columnName = "pr2_4k2")
    private String workaround;
    /** ������� */
    @FieldMeta(columnName = "prs_solution")
    private String solution;
    /** ����������� ���������� */
    @FieldMeta(columnName = "pro_workaround")
    private String commentToInitiator;
    /** ��������� */
    @FieldMeta(columnName = "pro_imp_oid")
    private EntityPriority entityPriority;
    /** ������� ���� */
    @FieldMeta(columnName = "pro_deadline")
    private Date deadline;
    /** ���������(����) */
    @FieldMeta(columnName = "pro_actualfinish")
    private Date resolvedDate;
    /** �������(����) */
    @FieldMeta(columnName = "pro_latefinish")
    private Date closureDate;
    /** ����������*/
    @FieldMeta(columnName = "pcf_boolean12")
    private Boolean isOverdue;
    /** ��� ����������*/
    @FieldMeta(columnName = "pcf_boolean12")
    private Person whoOverdue;
    /** ���� ����������*/
    @FieldMeta(columnName = "pro_planfinish")
    private Date planFinish;
    /** ������� �������� �������� �����*/
    @FieldMeta(columnName = "pcf_problemtext2")
    private String deferralReason;
    /** ��������� */ //todo ����������� ��������� � ��� Assignment
    private Assignment assignment;
    @FieldMeta(columnName = "pro_cat_oid")
    private EntityCategory category;
    @FieldMeta(columnName = "pro_cla_oid")
    private EntityClassification classification;

    //todo ����������
    @FieldMeta(columnName = "pro_clo_oid")
    private EntityClosureCode closureCode;
    /** ����� */
    @FieldMeta(columnName = "pro_poo_oid")
    private Folder folder;
    /** �� �������� � ����� ���������*/
    @FieldMeta(columnName = "pcf_boolean1")
    private Boolean notAttachInReport;

    //todo ����������� � ������
    //todo ������ � ������
    /** ���� ������� ������*/
    @FieldMeta(columnName = "pcf_problemdate2")
    private Date versionDate;
    /** ����������� �����������*/
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

    public Person getPerson() {
        return initiator;
    }

    public void setPerson(Person initiator) {
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

    public EntityPriority getEntityPriority() {
        return entityPriority;
    }

    public void setEntityPriority(EntityPriority entityPriority) {
        this.entityPriority = entityPriority;
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

    public Person getWhoOverdue() {
        return whoOverdue;
    }

    public void setWhoOverdue(Person whoOverdue) {
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
