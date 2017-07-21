import {TestGridOptions} from "./grid.config";

class TestController {
    static $inject = ['$scope', 'ModalAction','SD'];

    constructor($scope, ModalAction,SD) {
        this.$scope = $scope;
        this.ModalAction = ModalAction;
        this.SD = SD;
    }

    async log(){
        console.log("TEST");
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

    async loadChanges(){
        this.changes = await this.SD.Change.list();
    }

    logSC(){
        console.log(this.servicecall)
    }
}

export {TestController};