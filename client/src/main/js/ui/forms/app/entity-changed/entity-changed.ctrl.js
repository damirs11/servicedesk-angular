// Коды выхода
const DIALOG_EXIT = 0;
const DIALOG_CANCEL = 1;
const DIALOG_SAVE_EXIT = 2;

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
        this.$modalState.resolve(DIALOG_SAVE_EXIT);
    }

}

export {EntityChangedModalController}