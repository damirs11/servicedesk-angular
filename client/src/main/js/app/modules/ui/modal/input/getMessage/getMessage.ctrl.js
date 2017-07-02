export class GetMessageController{

    static $inject = ["$scope","$modalState","$modalData","$translate"];

    constructor($scope,$modalState,$modalData,$translate){
        this.$modalState = $modalState;
        this.$scope = $scope;

        this.header = $modalData.header;
        this.placeholder = $modalData.placeholder;
        this.maxLength = $modalData.maxLength || 256;
        this.minLength = $modalData.minLength || 0;
        this.labelOk = $modalData.labelOk || $translate.instant("DIALOGS_OK");
        this.labelClose = $modalData.labelClose || $translate.instant("DIALOGS_CLOSE");
        this.inputMessage = '';
    }

    complete() {
        if (this.inputError) {
            return; // отображаем сообщения об ошибках и не отправляем запрос на сервер
        }
        this.$modalState.resolve(this.inputMessage);
    }

    close() {
        this.$modalState.reject()
    }

    /**
     * Есть ли ошибки валидации текстового поля
     * @returns {boolean|*}
     */
    get inputError(){
        const $scope = this.$scope;
        return ($scope.inputMessageForm.$dirty && $scope.inputMessageForm.$invalid) || $scope.inputMessageForm.$pristine;
    }

    hitEnter(event) {
        if (angular.equals(event.keyCode, 13)) this.complete();
    }

}