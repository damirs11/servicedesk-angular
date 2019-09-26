import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCreatedModalController {

    @NGInject() $modalState;
    @NGInject() change;

    $onInit(){
        this.$modalState.onCancel = this::close
    }

    close() {
        this.$modalState.resolve()
    }
}

export {ChangeCreatedModalController as controller}