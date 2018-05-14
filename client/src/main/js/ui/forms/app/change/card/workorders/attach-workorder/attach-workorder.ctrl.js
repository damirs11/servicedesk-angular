import {NGInject, NGInjectClass} from "../../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class AttachWorkorderModalController {

    @NGInject() $scope;
    @NGInject() $modalState;
    @NGInject() SD;
    @NGInject("changeId") changeId;
    workorder;

    $onInit(){
        this.$modalState.onCancel = ::this.cancel;
    }

    cancel() {
        this.$modalState.resolve(null)
    }

    async loadWorkorders(text) {
        const filter = {};
        if (text) filter.no = text;
        return this.SD.Workorder.list(filter);
    }

    async attachWorkorder(){
        this.workorder.change = new this.SD.Change();
        this.workorder.change.id = this.changeId;
        await this.workorder.save();
        this.$modalState.resolve(this.workorder);
    }

    get isAttachButtonEnable(){
        if(this.workorder ){
            if(!this.isWorkorderAttached){
                return true;
            }
        }
        return false;
    }

    get isWorkorderAttached(){
        const selectedWorkorder = this.workorder;
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