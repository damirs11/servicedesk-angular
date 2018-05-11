package ru.it.sd.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import ru.it.sd.meta.ClassMeta;
import ru.it.sd.meta.FieldMeta;
import ru.it.sd.util.AppToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Модельный класс для "Изменения"
 *
 * @author quadrix
 * @since 03.05.2017
 */
@ClassMeta(tableName = "itsm_changes", tableAlias ="ch")
public class Change implements HasId, HasStatus, HasFolder, HasAssignment, Serializable {

	private static final long serialVersionUID = -857993162919153346L;

	/**
	 * Атрибуты, в которых фиксируется общение пользователей.
	 * Например: workaround и кастомный атрибут
	 */
	public static Long[] chatAttributes = new Long[]{
			HistoryType.CHANGE_INITIATOR.fieldId,
			HistoryType.CHANGE_MANAGER.fieldId
	};

	/** Уникальный идентификатор */
	@FieldMeta(columnName = "cha_oid", key = true)
	private Long id ;
	/** Номер */
	@FieldMeta(columnName = "cha_id")
	private Long no;
	/** Тема */
	@FieldMeta(columnName = "cha_description",  attribute = 556335107L)
	private String subject;
	/** Описание */
	@FieldMeta(columnName = "chi_information", tableAlias = "ci",  attribute = 738983997L)
	private String description;
	/** Решение*/
	@FieldMeta(columnName = "cha_solution", attribute = 724041772L)
	private String solution;
	/** Статус */
	@FieldMeta(columnName = "cha_sta_oid", attribute = 724041782L)
	private EntityStatus status;
	/** Приоритет */
	@FieldMeta(columnName = "cha_imp_oid",  attribute = 281478611337217L)
	private EntityPriority priority ;

	/** Категория*/
	@FieldMeta(columnName = "cha_cat_oid",  attribute = 724041784L)
	private EntityCategory category;

    /** Классификация*/
    @FieldMeta(columnName = "cha_cla_oid",  attribute = 165888L)
	private EntityClassification classification;

	/** Дата создания */
	@FieldMeta(columnName = "reg_created", attribute = 555679750L)
	private Date createdDate;
	/** Крайний срок */
	@FieldMeta(columnName = "cha_deadline", attribute = 556335111)
	private Date deadline;
	/** Реально начато*/
	@FieldMeta(columnName = "cha_actualstart", attribute = 556335112)
	private Date actualStart;
	/** Выполнено(дата)*/
	@FieldMeta(columnName = "cha_actualfinish", attribute = 556335112)
	private Date resolvedDate;
    /** Закртыто(дата)*/
	@FieldMeta(columnName = "cha_latefinish", attribute = 70370)
	private Date closureDate;
	/** План начала*/
	@FieldMeta(columnName = "cha_planstart", attribute = 70371L)
	private Date planStart;
	/** План завершения*/
	@FieldMeta(columnName = "cha_planfinish", attribute = 70372L)
	private Date planFinish;
	/** План продолжительность
	 * columnName = cha_planduration - не стандартное поле(время приходит в double)
	 * отдельно обрабатывается в ChangeExtractor
	 */
	@FieldMeta(columnName = "", attribute = 689504406L)
	private Date planDuration;

	@FieldMeta(columnName = "", attribute = 665649208)
	private Assignment assignment;

	/** Инициатор изменения */
	@FieldMeta(columnName = "cha_requestor_per_oid", attribute = 281478292766727L)
	private Person initiator;
	/** Менеджер изменения */
	@FieldMeta(columnName = "cha_per_man_oid",  attribute = 281483590631438L)
	private Person manager;
	/** Код завершения */
	@FieldMeta(columnName = "cha_closurecode", attribute = 166006L)
	private EntityClosureCode closureCode;
	/** Система*/
	@FieldMeta(columnName = "ccu_changecode1", attribute = 165964L)
	private EntityCode1 system;

	@FieldMeta(columnName = "cha_poo_oid", attribute = 1032388614L)
	private Folder folder;

	@FieldMeta(columnName = "cha_cit_oid", attribute = 884867074L)
	private ConfigurationItem configurationItem;

    //Поля для получения доступа к вкладкам(согласование, вложения, наряды, взаимосвязи)
	@FieldMeta(columnName = "", attribute = 281478248988673L)
	private long approval;

	@FieldMeta(columnName = "", attribute = 68465L)
	private long attachment;

	@FieldMeta(columnName = "", attribute = 724041786L)
	private long workorders;

	@FieldMeta(columnName = "", attribute = 166009L)
	private long relations;

	@FieldMeta(columnName = "cha_tem_oid" )
	private Template template;

	@FieldMeta(columnName = "ccu_changetext1", tableAlias = "ccu")
    private String commentToExecutor;

    @FieldMeta(columnName = "ccu_changetext7", tableAlias = "ccu")
    private String commentToManager;

    @FieldMeta(columnName = "cha_workaround")
    private String commentToInitiator;

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

    public EntityCategory getCategory() { return category; }

    public void setCategory(EntityCategory category) { this.category = category; }

    public EntityClassification getClassification() { return classification; }

    public void setClassification(EntityClassification classification) { this.classification = classification; }

    public EntityClosureCode getClosureCode() {
        return closureCode;
    }

    public void setClosureCode(EntityClosureCode closureCode) {
        this.closureCode = closureCode;
    }
    @Override
    public Folder getFolder() {
        return folder;
    }
    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
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

	public Date getActualStart() {
		return actualStart;
	}

	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	public Date getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(Date closureDate) {
		this.closureDate = closureDate;
	}

	public Date getPlanStart() {
		return planStart;
	}

	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public Date getPlanFinish() {
		return planFinish;
	}

	public void setPlanFinish(Date planFinish) {
		this.planFinish = planFinish;
	}

	public Date getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(Date planDuration) {
		this.planDuration = planDuration;
	}

	public EntityCode1 getSystem() {
		return system;
	}

	public void setSystem(EntityCode1 system) {
		this.system = system;
	}

	public ConfigurationItem getConfigurationItem() {
		return configurationItem;
	}

	public void setConfigurationItem(ConfigurationItem configurationItem) {
		this.configurationItem = configurationItem;
	}

    public String getCommentToExecutor() {
        return commentToExecutor;
    }

    public void setCommentToExecutor(String commentToExecutor) {
        this.commentToExecutor = commentToExecutor;
    }

    public String getCommentToManager() {
        return commentToManager;
    }

    public void setCommentToManager(String commentToManager) {
        this.commentToManager = commentToManager;
    }

    public String getCommentToInitiator() {
        return commentToInitiator;
    }

    public void setCommentToInitiator(String commentToInitiator) {
        this.commentToInitiator = commentToInitiator;
    }

    @Override
	public Assignment getAssignment() {
		return assignment;
	}

	@Override
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, AppToStringStyle.getInstance());
	}

}