import {WORKORDER_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_WORKORDER} from "../../../../../../api/entity/util/entity-type-list";


@NGInjectClass()
class WorkorderCardViewController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() workorderId;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() $pageLock;

    requiredFields = [
        "status","subject","description","category","initiator",
        "assignment.workgroup","assignment.executor", "deadline",
        "priority",
    ];

    /**
     * Наряд
     */
    workorder;
    /**
     * Режим редактирования
     */
    editing;


    $onInit() {
        this.msgTypes = WORKORDER_MESSAGE_TYPES;
        this.workorder = new this.SD.Workorder(this.workorderId);
        this.accessRules = this.workorder.accessRules;
        this.registerLeaveEditListener();
    }

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.workorder.save();
        this.editing = false;
    }
    cancelEditing(){
        this.workorder.reset();
        this.editing = false;
    }

    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! В сущность были внесены изменение, сохранить их перед уходом?")
            .setCondition(() => this.editing && this.workorder.checkModified())
            .addAction("Да",async () => {
                await this.saveEditing();
                return true;
            })
            .addAction("Нет", () => {
                this.workorder.reset();
                return true;
            })
            .addAction("Отмена", () => false)
            .lock();
    }

    checkUnsavedModifies(){
        return this.workorder.checkModified() || this.workorder.assignment.checkModified();
    }

    get saveButtonTitle(){
        if (!this.checkUnsavedModifies()) return "Нет изменений для сохранения";
        if (!this.isRequiredFieldsFilled) return "Не заполнены обязательные поля";
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.workorder;
            for (let i = 0; i < subnames.length; i++) {
                const subname = subnames[i];
                const value = obj[subname];
                if (value == null) return false;
                obj = obj[subname]
            }
        }
        return true;
    }

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable: "1", hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.workorder.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable: "1"};
        if (text) filter.name_like = text;
        const executor = this.workorder.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: this.SD.Workorder.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClosureCode(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityClosureCode.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }
    async loadChanges(text) {
        const filter = {};
        if (text) filter.no = text;
        return this.SD.Change.list(filter);
    }
}

export {WorkorderCardViewController as controller}