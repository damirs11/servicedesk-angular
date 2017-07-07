import {TestGridOptions} from "./grid.config";

class TestController {
    static $inject = ['$scope', 'ModalAction', '$injector'];

    constructor($scope, ModalAction, $injector) {
        this.$scope = $scope;
        this.ModalAction = ModalAction;
        this.gridOptions = $injector.instantiate(TestGridOptions, {
            $scope: $scope
        });
    }

    errorClick() {
        this.ModalAction.alert(this.$scope, {
            header: 'Ошибка',
            msg: 'Превышен лимит стоимости заказа на производство',
            style: 'dialog-header-error'
        });
        //aplanaDialogs.error('Ошибка', 'Превышен лимит стоимости заказа на производство', {});
    }
}

export {TestController};