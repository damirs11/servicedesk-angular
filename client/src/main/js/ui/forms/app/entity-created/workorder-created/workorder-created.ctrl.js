import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class WorkorderCreatedModalController {

    @NGInject() $modalState;
    @NGInject() workorder;

    $onInit(){
        this.$modalState.onCancel = undefined
    }

    backToList() {
        this.$modalState.resolve(false)
    }

    openEntity() {
        this.$modalState.resolve(true);
    }

}

export {WorkorderCreatedModalController as controller}