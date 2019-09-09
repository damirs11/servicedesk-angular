AlertController.$inject = ["$modalState", "header", "msg", "style"];
export class AlertController {
    constructor($modalState, header, msg, style) {
        this.$modalState = $modalState;

        this.header = header;
        this.msg = msg;
        this.dialogStyle = style;
    }

    close() {
        this.$modalState.resolve()
    }

}