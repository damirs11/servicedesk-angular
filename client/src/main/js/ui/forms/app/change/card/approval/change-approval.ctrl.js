import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_APPROVAL} from "../../../../../../api/entity/util/entity-type-list";
import {
    APPROVAL_STATUSES
} from "../../../../../../api/entity/util/status-list";

const ERROR_APPROVAL_READ_DISALLOWED = Symbol("ERROR_APPROVAL_READ_DISALLOWED");



@NGInjectClass()
class ChangeCardApprovalController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() $grid;
    @NGInject() $pageLock;
    @NGInject() $injector;
    @NGInject() Session;
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";
    /**
     * Промис подгрузки данных
     */
    loadingPromise = null;
    /**
     * Ошибка, возникшая в стейте
     */
    stateError;
    /**
     * Права доступа согласования
     */
    accessRules;
    /**
     * согласование
     */
    approval;
    /**
     * Согласование, которое не изменяется клиентом
     * Необходимо дял исключения проблем проверки прав
     * при редактировании.
     * Права проверяются на dummyApproval, редактируется approval
     */
    dummyApproval;

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.approval.save();
        this.grid.fetchData();
        this.change.load(); // ToDo пересмотреть необходимость вызывать load здесь
        this.editing = false;
    }
    cancelEditing(){
        this.approval.reset();
        this.editing = false;
    }

    async $onInit() {
        const change = this.change = new this.SD.Change(this.changeId);
        if (!this.change.accessRules.isReadApprovalAllowed) return;
        const grid = this.grid = new this.$grid.ApproverVoteGrid(this.$scope,this.SD,change);
        this.loadingPromise = this.change.getApproval();
        this.approval = await this.loadingPromise;
        this.dummyApproval = new this.SD.Approval(this.approval.id);
        grid.fetchData();
        this.registerLeaveListener();
    }


    registerLeaveListener(){
        this.$pageLock(this.$scope)
            .setTitle("Несохраненные изменения")
            .setText("Внимание! В согласование были внесены изменение, сохранить их перед уходом?")
            .setCondition(() => this.approval.checkModified())
            .addAction("Да",async () => {
                await this.approval.save();
                return true;
            }).addAction("Нет", () => {
                this.approval.reset();
                return true;
            }).addAction("Отмена", () => false)
            .lock();
    }

    get stateErrorIsApprovalReadDisallowed() {
        return this.stateError == ERROR_APPROVAL_READ_DISALLOWED;
    }

    sortedStatusList = [];
    async _loadStatuses(){
        await this.SD.EntityStatus.list({entityTypeId:TYPEID_APPROVAL});
        // Т.к строкой выше мы их всех подгрузили, то мы можем просто создавать их по ID. Все данные уже есть.
        this.sortedStatusList.push(new this.SD.EntityStatus(APPROVAL_STATUSES.PREPARING));
        this.sortedStatusList.push(new this.SD.EntityStatus(APPROVAL_STATUSES.BEGIN));
        this.sortedStatusList.push(new this.SD.EntityStatus(APPROVAL_STATUSES.COMPLETE));
    }

    async getAllowedStatuses(){
        if (!this.sortedStatusList.length) {
            await this._loadStatuses();
        }

        const result = [];
        if (this.isStatusPreparingAvailable) result.push(this.sortedStatusList[0]);
        if (this.isStatusBeginAvailable) result.push(this.sortedStatusList[1]);
        if (this.isStatusCompleteAvailable) result.push(this.sortedStatusList[2]);
        return result;
    }

    /**
     * Возвращает число, указывающее какие статусы доступны для выбора
     * Необходимо для компонента sd-dropdown по статусу. При измекении числа - он делает fetch
     * Просто массив туда передать нельзя из-за infinite-digest
     * @return {*}
     */
    get availableStatusesCode(){
        return this.isStatusPreparingAvailable
            + this.isStatusBeginAvailable*2
            + this.isStatusCompleteAvailable*4
    }

    get isStatusPreparingAvailable() {
        return true;
    }
    get isStatusBeginAvailable() {
        const approval = this.dummyApproval;
        if (approval.status == null) return false;
        if (approval.status.id == APPROVAL_STATUSES.PREPARING) {
            if (!approval.numberOfApproversRequired) return false;
            return true;
        } else if (approval.status.id == APPROVAL_STATUSES.BEGIN) {
            return true
        } else if (approval.status.id == APPROVAL_STATUSES.COMPLETE) {
            return false;
        }
        return true;
    }
    get isStatusCompleteAvailable() {
        const approval = this.dummyApproval;
        if (approval.status == null) return false;
        if (approval.status.id == APPROVAL_STATUSES.BEGIN) return true;
        return false;
    }

    get maxApproversRequired(){
        if (this.approval.numberOfApprovers) return this.approval.numberOfApprovers;
        return 0;

    }

    isEditableField() {
        if (this.approval.status) {
            if (this.editing && this.approval.status.id == APPROVAL_STATUSES.PREPARING) return true;
        }
        return false;
    }

    isEditAllow(){
        if(this.change.accessRules.isUpdateApprovalAllowed && this.$isApprovalInitiator) return true;
        return false;
    }

    get $isApprovalInitiator(){
        //Если текущий пользователь является инициатором согласования
        return this.dummyApproval.initiator
            && (this.dummyApproval.initiator.id == this.Session.user.person.id)
    }

    get $approvalResult(){
        if (!this.dummyApproval.status) return;
        const approved = this.approval.numberOfApproversApproved;
        const required = this.approval.numberOfApproversRequired;
        if (this.dummyApproval.status.id == APPROVAL_STATUSES.COMPLETE ) {
            if (approved == required) return "Согласовано";
            return "Не согласовано"
        }
        return "Не готово";
    }

    async loadWorkgroups(text){
        const filter = {selectable:"1"};
        if (text) filter.fulltext = text;
        return this.SD.Workgroup.list(filter);
    }
}

export {ChangeCardApprovalController as controller}