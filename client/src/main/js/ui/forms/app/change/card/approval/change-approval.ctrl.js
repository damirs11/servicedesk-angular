import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

const ERROR_APPROVAL_READ_DISALLOWED = Symbol("ERROR_APPROVAL_READ_DISALLOWED");

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
        if (!this.change.entityAccess.readApprovalAllowed) return;
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
}

export {ChangeCardApprovalController as controller}