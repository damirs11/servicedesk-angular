import {CHANGE_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCardViewController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() $pageLock;

    /**
     * Дублирование change.entityAccess
     * для краткости в html
     */
    entityAccess;

    constructor(){
        this.msgTypes = CHANGE_MESSAGE_TYPES;
    }

    async $onInit() {
        this.change = new this.SD.Change(this.changeId);
        this.accessRules = await this.change.accessRules;
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

    registerLeaveEditListener() {
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! В сущность были внесены изменение, сохранить их перед уходом?")
            .setCondition(() => this.change.checkModified())
            .addAction("Да",async () => {
                await this.change.save();
                return true;
            }).addAction("Нет", () => {
                this.change.reset();
                return true;
            }).addAction("Отмена", () => false)
            .lock();
    }

    // Методы подгрузки, для состояния редактирования

    // Проверка, есть ли несохраненные изменения. Влияет на кнопку "Сохранить"
    checkUnsavedModifies(){
        return this.change.checkModified() || this.change.assignment.checkModified();
    }

    async loadInititators(text) {
        const filter = {};
        if (text) filter.fulltext = text;
        return this.SD.Person.list(filter);
    }

    async loadManagers(text) {
        const filter = {};
        if (text) filter.fulltext = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {};
        if (text) filter.fulltext = text;
        const workgroup = this.change.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.fulltext = text;
        const executor = this.change.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.SD.Change.$entityTypeId};
        if (text) filter.fulltext = text;
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