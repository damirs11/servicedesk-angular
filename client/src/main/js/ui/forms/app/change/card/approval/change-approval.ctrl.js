import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCardApprovalController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() $grid;
    @NGInject() $pageLock;

    loadingPromise = null;

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
}

export {ChangeCardApprovalController as controller}