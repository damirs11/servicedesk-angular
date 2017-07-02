export class ConfirmController{

    static $inject = ["$modalState","$modalData", "$translate"];

    constructor($modalState,$modalData, $translate){
        this.$modalState = $modalState;

        this.header = $modalData.header;
        this.msg = $modalData.msg;
        this.labelYes = $modalData.labelYes || $translate.instant("DIALOGS_YES");
        this.labelNo = $modalData.labelNo || $translate.instant("DIALOGS_NO");
        this.labelClose = $modalData.labelClose || $translate.instant("DIALOGS_CLOSE");
    }

    close() {
        this.$modalState.reject()
    }

    yes() {
        this.$modalState.resolve(true);
    }

    no() {
        this.$modalState.resolve(false);
    }
}