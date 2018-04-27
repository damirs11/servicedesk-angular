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

    /**
     * Дублирование workorder.entityAccess
     * для краткости в html
     */
    entityAccess;
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

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fulltext = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable: "1", hasAccount: ""};
        if (text) filter.fulltext = text;
        const workgroup = this.workorder.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable: "1"};
        if (text) filter.fulltext = text;
        const executor = this.workorder.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityStatus.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: TYPEID_WORKORDER};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }
}

export {WorkorderCardViewController as controller}