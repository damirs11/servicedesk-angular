import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class AttachWorkorderModalController {
    @NGInject() $scope;
    @NGInject() $modalState;
    @NGInject() SD;
    @NGInject("entity") entity;
    @NGInject("workorderField") workorderField;
    busy = false;

    $onInit(){
        this.$modalState.onCancel = ::this.cancel;
    }

    cancel() {
        if (!this.canCloseDialog) return;
        this.$modalState.resolve(null)
    }

    async loadWorkorders(text) {
        const filter = {};
        if (text) filter.no = text;
        return this.SD.Workorder.list(filter);
    }

    async attachWorkorder(){
        if (this.busy) return;
        this.busy = "attaching";
        this.workorder[this.workorderField] = this.entity;
        await this.workorder.save();
        this.$modalState.resolve(this.workorder);
        this.busy = false;
    }

    get attachButtonTitle(){
        if (!this.workorder) return "Выберите наряд";
        if (this.isWorkorderAttached) return "Наряд уже прикреплен.";
        return "";
    }

    get isAttachButtonDisabled(){
        if (this.busy || !this.workorder) return true;
        return this.isWorkorderAttached;
    }

    get canCloseDialog(){
        return !this.busy
    }

    get isWorkorderAttached(){
        const selectedWorkorder = this.workorder;
        if (!selectedWorkorder) return false;
        let attached = false;
        this.$scope.$parent.ctrl.grid.data.forEach(function(val){
            if(val.id == selectedWorkorder.id) {
                attached = true;
            }
        });
        return attached;
    }
}

export {AttachWorkorderModalController as controller}