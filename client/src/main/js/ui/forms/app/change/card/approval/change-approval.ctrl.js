import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";
import {TYPEID_APPROVAL} from "../../../../../../api/entity/entity-type-list";

const ERROR_APPROVAL_READ_DISALLOWED = Symbol("ERROR_APPROVAL_READ_DISALLOWED");

const APPROVAL_PREPARING_STATUS_ID = 281478256721931;
const APPROVAL_BEGIN_STATUS_ID = 281478256721929;
const APPROVAL_COMPLETE_STATUS_ID = 281478256721933;
const CHANGE_APPROVAL_COMPLETE_STATUS_ID = 3095134328;

@NGInjectClass()
class ChangeCardApprovalController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() $grid;
    @NGInject() $pageLock;
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

    // Статус стейта, редактирование/просмотр
    editing = false;
    startEditing(){
        this.editing = true;
    }
    async saveEditing(){
        await this.approval.save();
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
        this.sortedStatusList.push(new this.SD.EntityStatus(APPROVAL_PREPARING_STATUS_ID));
        this.sortedStatusList.push(new this.SD.EntityStatus(APPROVAL_BEGIN_STATUS_ID));
        this.sortedStatusList.push(new this.SD.EntityStatus(APPROVAL_COMPLETE_STATUS_ID));
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

    get isStatusPreparingAvailable() {
        return true;
    }
    get isStatusBeginAvailable() {
        const approval = this.approval;
        if (approval.status.id == APPROVAL_PREPARING_STATUS_ID) {
            if (!approval.subject || !approval.deadline || !approval.numberOfApproversRequired) return false;
            return true;
        } else if (approval.status.id == APPROVAL_BEGIN_STATUS_ID) {
            return true
        } else if (approval.status.id == APPROVAL_COMPLETE_STATUS_ID) {
            return false;
        }
        return true;
    }
    get isStatusCompleteAvailable() {
        const approval = this.approval;
        if (approval.status.id == APPROVAL_BEGIN_STATUS_ID) return true;
        return false;
    }
}

export {ChangeCardApprovalController as controller}