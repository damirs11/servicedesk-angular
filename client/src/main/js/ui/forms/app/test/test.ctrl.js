import {TestGridOptions} from "./grid.config";

class TestController {
    static $inject = ['$scope', 'ModalAction'];

    constructor($scope, ModalAction) {
        this.$scope = $scope;
        this.ModalAction = ModalAction;
        // this.gridOptions = new TestGridOptions($scope);
    }

    $onInit(){
        this.servicecall = {
            subject: "Тема заявки!",
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

    logSC(){
        console.log(this.servicecall)
    }
}

export {TestController};