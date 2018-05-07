import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class EntityChangedModalController {

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

export {EntityChangedModalController}