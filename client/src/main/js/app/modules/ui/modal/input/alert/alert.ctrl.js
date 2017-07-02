export class AlertController{

    static $inject = ["$modalState","$modalData"];

    constructor($modalState,$modalData){
        this.$modalState = $modalState;

        this.header = $modalData.header;
        this.msg = $modalData.msg;
        this.dialogStyle = $modalData.style;
    }

    close() {
        this.$modalState.resolve()
    }

}