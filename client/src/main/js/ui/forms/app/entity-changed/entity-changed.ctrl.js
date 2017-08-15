// Коды выхода
const DIALOG_SAVE = 0;
const DIALOG_EXIT = 1;
const DIALOG_CANCEL = 2;

class EntityChangedModalController {

    static $inject = ["$modalState"];

    constructor($modalState) {
        this.$modalState = $modalState;

        $modalState.onCancel = ::this.cancel;
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