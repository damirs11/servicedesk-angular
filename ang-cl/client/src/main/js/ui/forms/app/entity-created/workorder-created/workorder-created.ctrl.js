import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class WorkorderCreatedModalController {

    @NGInject() $modalState;
    @NGInject() workorder;

    $onInit(){
        this.$modalState.onCancel = thisclose
    }

    close() {
        this.$modalState.resolve()
    }

}

export {WorkorderCreatedModalController as controller}