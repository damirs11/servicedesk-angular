import {CHANGE_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {
    CHANGE_STATUSES
} from "../../../../../../api/entity/util/status-list";

@NGInjectClass()
class ChangeCardViewController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() entity;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() $pageLock;
    /**
     * Дублирование change.entityAccess
     * для краткости в html
     */
    entityAccess;
    /**
     * Изменение
     */
    change;
    /**
     * Режим редактирования
     */
    editing;

    requiredFields = [
        "category","classification","deadline","subject","description",
        "manager","initiator","priority","assignment.workgroup","assignment.executor"
    ];

    async $onInit() {
        this.msgTypes = CHANGE_MESSAGE_TYPES;
        this.change = this.entity;
        this.accessRules = this.change.accessRules;
        this.registerLeaveEditListener();

    }

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.change.save();
        this.editing = false;
    }
    cancelEditing(){
        this.change.reset();
        this.editing = false;
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.change;
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
            .setText("Внимание! В сущность были внесены изменения, сохранить их перед уходом?")
            .setCondition(() => this.editing && this.change.checkModified())
            .addAction("Да",async () => {
                await this.saveEditing();
                return true;
            })
            .addAction("Нет", () => {
                this.change.reset();
                return true;
            })
            .addAction("Отмена", () => false)
            .lock();
    }

    // Методы подгрузки, для состояния редактирования

    // Проверка, есть ли несохраненные изменения. Влияет на кнопку "Сохранить"
    checkUnsavedModifies(){
        return this.change.checkModified() || this.change.assignment.checkModified();
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
        const workgroup = this.change.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.name_like = text;
        const executor = this.change.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        const status = this.change.status;
        //Выборка статусов
        let statuses = [];
        //Если статус зарегистрировано, то доступен статус арегистрировано
        if(status.id == CHANGE_STATUSES.REGISTERED) {
            filter.id = CHANGE_STATUSES.REGISTERED;
        }
        else {
            //Если статус не зарегистрировано, то доступны статусы Подготовка;На согласовании;Согласование завершено;Реализация;Решено;Закрыто;
            filter.id =
                CHANGE_STATUSES.PREPARING + ";" +
                CHANGE_STATUSES.ON_APPROVE + ";" +
                CHANGE_STATUSES.APPROVAL_COMPLETE + ";" +
                CHANGE_STATUSES.EXECUTING + ";" +
                CHANGE_STATUSES.RESOLVED + ";" +
                CHANGE_STATUSES.CLOSED;
        }
        //Если статус закрыто, то доступен статус закрыто
        if(status.id == CHANGE_STATUSES.CLOSED) filter.id = CHANGE_STATUSES.CLOSED;
        //Если Подготовка || Реализация || Решено ,то доступны статусы Подготовка;На согласовании;Реализация;Решено;Закрыто;
        if(status.id == CHANGE_STATUSES.PREPARING || status.id == CHANGE_STATUSES.EXECUTING || status.id == CHANGE_STATUSES.RESOLVED){
            filter.id =
                CHANGE_STATUSES.PREPARING + ";" +
                CHANGE_STATUSES.ON_APPROVE + ";" +
                CHANGE_STATUSES.EXECUTING + ";" +
                CHANGE_STATUSES.RESOLVED + ";" +
                CHANGE_STATUSES.CLOSED;
        }
        return this.SD.EntityStatus.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityClassification.list(filter);
    }

    async loadSystems(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode1.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }

    async loadConfigurationItems(text) {
        const filter = {};
        if (text) filter.searchCode_like = text;
        return this.SD.ConfigurationItem.list(filter);
    }

    async loadClosureCode(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.name = text;
        return this.SD.EntityClosureCode.list(filter);
    }
}

export {ChangeCardViewController as controller}