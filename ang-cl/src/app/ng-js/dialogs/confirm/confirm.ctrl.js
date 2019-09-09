ConfirmController.$inject = ["$modalState", "header", "msg", "required"];
class ConfirmController{
    constructor($modalState, header, msg, required){
        this.$modalState = $modalState;

        this.header = header;
        this.msg = msg;
        this.required = required;
        if (required) $modalState.onCancel = null;
    }

    close() {
        this.$modalState.resolve(null)
    }

    yes() {
        this.$modalState.resolve(true);
    }

    no() {
        this.$modalState.resolve(false);
    }
}

export {ConfirmController as controller}