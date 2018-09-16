import {PROBLEM_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {
    PROBLEM_STATUSES
} from "../../../../../../api/entity/util/status-list";
import {TYPEID_PROBLEM} from "../../../../../../api/entity/util/entity-types";

@NGInjectClass()
class ProblemCardViewController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() problemId;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() $pageLock;
    /**
     * Дублирование problem.entityAccess
     * для краткости в html
     */
    entityAccess;
    /**
     * Сущность - "Проблема"
     */
    problem;
    /**
     * Режим редактирования
     */
    editing;

    requiredFields = [
        // "category","classification","deadline","subject","description",
        // "manager","initiator","priority","assignment.workgroup","assignment.executor"
    ];

    async $onInit() {
        this.msgTypes = PROBLEM_MESSAGE_TYPES;
        this.problem = new this.SD.Problem(this.problemId);
        this.accessRules = this.problem.accessRules;
        this.registerLeaveEditListener();

    }

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.problem.save();
        this.editing = false;
    }
    cancelEditing(){
        this.problem.reset();
        this.editing = false;
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.problem;
            for (let i = 0; i < subnames.length; i++) {
                const subname = subnames[i];
                const value = obj[subname];
                if (value == null) return false;
                obj = obj[subname]
            }
        }
        return true;
    }

    get saveButtonTitle(){
        if (!this.checkUnsavedModifies()) return "Нет изменений для сохранения";
        if (!this.isRequiredFieldsFilled) return "Не заполнены обязательные поля";
    }

    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! В сущность были внесены изменение, сохранить их перед уходом?")
            .setCondition(() => this.editing && this.problem.checkModified())
            .addAction("Да",async () => {
                await this.saveEditing();
                return true;
            })
            .addAction("Нет", () => {
                this.problem.reset();
                return true;
            })
            .addAction("Отмена", () => false)
            .lock();
    }

    // Методы подгрузки, для состояния редактирования

    // Проверка, есть ли несохраненные изменения. Влияет на кнопку "Сохранить"
    checkUnsavedModifies(){
        return this.problem.checkModified() || this.problem.assignment.checkModified();
    }

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadManagers(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable:"1", hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.problem.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.name_like = text;
        const executor = this.problem.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        return this.SD.EntityStatus.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        if (text) filter.fulltext = text;
        return this.SD.EntityClassification.list(filter);
    }

    async loadSystems(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode1.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }

    async loadConfigurationItems(text) {
        const filter = {};
        if (text) filter.searchCode_like = text;
        return this.SD.ConfigurationItem.list(filter);
    }

    async loadClosureCode(text) {
        const filter = {entityTypeId: TYPEID_PROBLEM};
        if (text) filter.name = text;
        return this.SD.EntityClosureCode.list(filter);
    }
}

export {ProblemCardViewController as controller}