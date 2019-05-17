import {SERVICECALL_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";
import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {SERVICECALL_STATUSES} from "../../../../../../api/entity/util/status-list";

@NGInjectClass()
class ServiceCallCardViewController{
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() ModalAction;
    @NGInject() $state;
    @NGInject() $transitions;
    @NGInject() $pageLock;
    @NGInject() entity;

    /**
     * Заявка
     */
    serviceCall;
    /**
     * Режим редактирования/просмотр
     */
    editing = false;

    requiredFields = [
        "category","classification","deadline","subject","description",
        "initiator","priority","assignment.workgroup","assignment.executor"
    ];

    async $onInit() {
        console.debug('servicecall view init');

        this.msgTypes = SERVICECALL_MESSAGE_TYPES;
        this.serviceCall = this.entity;
        this.accessRules = this.serviceCall.accessRules;
        this.registerLeaveEditListener();
    }

    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.serviceCall.save();
        this.editing = false;
    }
    cancelEditing(){
        this.serviceCall.reset();
        this.editing = false;
    }

    get isRequiredFieldsFilled() {
        for(const fieldName of this.requiredFields) {
            const subnames = fieldName.split("."); // Сложное имя поля (пр. assignment.executor)
            let obj = this.serviceCall;
            for (let i = 0; i < subnames.length; i++) {
                const subname = subnames[i];
                const value = obj[subname];
                if (value === null) return false;
                obj = obj[subname];
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
            .setCondition(() => this.editing && this.serviceCall.checkModified())
            .addAction("Да", async () => {
                await this.saveEditing();
                return true;
            })
            .addAction("Нет", () => {
                this.serviceCall.reset();
                return true;
            })
            .addAction("Отмена", () => false)
            .lock();
    }

    // Методы подгрузки, для состояния редактирования

    // Проверка, есть ли несохраненные изменения. Влияет на кнопку "Сохранить"
    checkUnsavedModifies(){
        return this.serviceCall.checkModified() || this.serviceCall.assignment.checkModified();
    }

    async loadOrganizations(text) {
        if (text) {
            const filter = {selectable:"1"};
            filter.name_like = text;
            return this.SD.Organization.list(filter);
        }
        const caller = this.serviceCall.caller;
        if (!text && caller && caller.organization) { // если задан заявитель, то должна отображаться его организация
            this.serviceCall.organization = caller.organization;
            return [caller.organization];
        }
    }

    async loadCallers(text) {
        const filter = {selectable:"1"};
        if (text) {
            filter.name_like = text;
        }
        const organization = this.serviceCall.organization;
        if (organization) {
            if (!text) {
                this.serviceCall.caller = null;
            }
            filter.organization = organization.id; // ищем только среди сотрудников организации
        }
        return this.SD.Person.list(filter);
    }

    async loadServices(text) {
        const organization = this.serviceCall.organization;
        if (organization) {
            const filter = {selectable:"1"};
            if (text) {
                filter.name_like = text;
            }
            filter.organization = organization.id;
            return this.SD.Service.list(filter);
        }
        return [];
    }

    async loadInititators(text) {
        const filter = {selectable:"1"};
        if (text) filter.fullname_like = text;
        return this.SD.Person.list(filter);
    }

    async loadExecutors(text){
        const filter = {selectable:"1", hasAccount: ""};
        if (text) filter.fullname_like = text;
        const workgroup = this.serviceCall.assignment.workgroup;
        if (workgroup) filter.workgroupId = workgroup.id;
        return this.SD.Person.list(filter);
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.name_like = text;
        const executor = this.serviceCall.assignment.executor;
        if (executor) filter.personId = executor.id;
        return this.SD.Workgroup.list(filter);
    }

    async loadStatuses(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        //Выборка статусов
        const status = this.serviceCall.status;
        switch (status.id) {
            case SERVICECALL_STATUSES.REGISTERED:
                filter.id =
                    SERVICECALL_STATUSES.REGISTERED + ';' +
                    SERVICECALL_STATUSES.TO_ENGINEER;
                break;
            case SERVICECALL_STATUSES.TO_ENGINEER:
                filter.id =
                    SERVICECALL_STATUSES.TO_ENGINEER + ';' +
                    SERVICECALL_STATUSES.CLOSED;
                break;
            case SERVICECALL_STATUSES.EXECUTING:
                filter.id =
                    SERVICECALL_STATUSES.PAUSED + ';' +
                    SERVICECALL_STATUSES.EXECUTING + ';' +
                    SERVICECALL_STATUSES.RESOLVED + ';' +
                    SERVICECALL_STATUSES.CLOSED;
                break;
            case SERVICECALL_STATUSES.RESOLVED:
                filter.id =
                    SERVICECALL_STATUSES.TO_ENGINEER + ';' +
                    SERVICECALL_STATUSES.CLOSED;
                break;
            case SERVICECALL_STATUSES.CLOSED:
                filter.id =
                    SERVICECALL_STATUSES.CLOSED;
                break;
        }


        return this.SD.EntityStatus.list(filter);
    }

    async loadPriorities(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityPriority.list(filter);
    }

    async loadCategories(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCategory.list(filter);
    }

    async loadClassifications(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityClassification.list(filter);
    }

    async loadSystems(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.EntityCode1.list(filter);
    }

    async loadFolders(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.fulltext = text;
        return this.SD.Folder.list(filter);
    }

    async loadConfigurationItems(text) {
        const filter = {};
        if (text) filter.searchCode_like = text;
        return this.SD.ConfigurationItem.list(filter);
    }

    async loadClosureCode(text) {
        const filter = {entityTypeId: this.SD.ServiceCall.$entityTypeId};
        if (text) filter.name = text;
        return this.SD.EntityClosureCode.list(filter);
    }
}

export {ServiceCallCardViewController as controller};