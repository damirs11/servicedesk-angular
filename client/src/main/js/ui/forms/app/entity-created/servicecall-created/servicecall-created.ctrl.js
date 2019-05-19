import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ServiceСallCreatedModalController {

    @NGInject() $modalState;
    @NGInject() entity;

    $onInit(){
        this.$modalState.onCancel = this::close
    }

    close() {
        this.$modalState.resolve()
    }
}

export {ServiceСallCreatedModalController as controller}