import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ProblemCreatedModalController {

    @NGInject() $modalState;
    @NGInject() problem;

    $onInit(){
        this.$modalState.onCancel = thisclose
    }

    close() {
        this.$modalState.resolve()
    }
}

export {ProblemCreatedModalController as controller}