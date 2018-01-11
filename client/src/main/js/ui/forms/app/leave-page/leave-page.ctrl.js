import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class LeavePageModalController {

    @NGInject() $modalState;
    @NGInject() text;
    @NGInject() actionInfos;
    @NGInject() title;

    $onInit(){
        this.$modalState.onCancel = () => this.click(null);
    }

    getButtonClasses(action){
        if (!action.btn) return "btn btn-default";
        else return action.btn;
    }

    click(value){
        this.$modalState.resolve(value)
    }

}

export {LeavePageModalController as controller}