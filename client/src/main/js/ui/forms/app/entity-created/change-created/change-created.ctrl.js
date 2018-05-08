import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCreatedModalController {

    @NGInject() $modalState;
    @NGInject() change;

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

export {ChangeCreatedModalController as controller}