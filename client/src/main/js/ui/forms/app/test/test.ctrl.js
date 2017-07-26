import {TestGridOptions} from "./grid.config";

class TestController {
    static $inject = ['$scope', 'ModalAction','SD'];

    constructor($scope, ModalAction,SD) {
        this.$scope = $scope;
        this.ModalAction = ModalAction;
        this.SD = SD;

        this.enabled = true;
        this.minDate = moment().subtract(2,"days").toDate();
        this.maxDate = moment().add(1,"day").toDate();
    }

    $onInit(){
        this.servicecall = {
            subject: "Длинная тема заявки",
            change: this.SD.Change.parse({no:-15})
        }
    }

    errorClick() {
        this.ModalAction.alert(this.$scope, {
            header: 'Ошибка',
            msg: 'Превышен лимит стоимости заказа на производство',
            style: 'dialog-header-error'
        });
        //aplanaDialogs.error('Ошибка', 'Превышен лимит стоимости заказа на производство', {});
    }

    async loadChanges(text){
        console.log(text);
        if (text == "0") return this.changes = null;
        this.changes = await this.SD.Change.list();
        this.servicecall.change = this.changes[0];
    }

    logSC(){
        console.log(this.servicecall)
    }
}

export {TestController};