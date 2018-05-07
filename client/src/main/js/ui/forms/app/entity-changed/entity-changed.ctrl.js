import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";
// Коды выхода
const DIALOG_SAVE = 0;
const DIALOG_EXIT = 1;
const DIALOG_CANCEL = 2;

@NGInjectClass()
class EntityChangedModalController {

    @NGInject() $modalState;

    $onInit(){
        this.$modalState.onCancel = ::this.cancel;
    }

    cancel() {
        this.$modalState.resolve(DIALOG_CANCEL)
    }

    exit() {
        this.$modalState.resolve(DIALOG_EXIT);
    }

    save() {
        this.$modalState.resolve(DIALOG_SAVE);
    }

}

export {EntityChangedModalController}