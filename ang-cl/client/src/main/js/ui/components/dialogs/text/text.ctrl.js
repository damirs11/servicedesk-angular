export class GetMessageController {

    static $inject = ["$scope", "$modalState", "header", "placeholder", "value", "maxLength", "required"];

    constructor($scope, $modalState, header, placeholder, value, maxLength, required) {
        this.$scope = $scope;
        this.$modalState = $modalState;
        this.header = header;
        this.placeholder = placeholder;
        this.value = value || "";
        this.maxLength = maxLength;
        this.required = required;
        if (required) $modalState.onCancel = null; // Отменить клик в маску
    }

    submit() {
        if (!this.canSubmit) return;
        this.$modalState.resolve(this.value);
    }

    close() {
        this.$modalState.resolve(null)
    }

    get canSubmit() {
        if (this.value) return true;
        if (!this.required) return true;
        return false;
    }

    /**
     * Есть ли ошибки валидации текстового поля
     * @returns {boolean|*}
     */
    get inputError() {
        const form = this.$scope.inputMessageForm;
        return ((form.$dirty && form.$invalid) || form.$pristine)
            && (form.$submitted || form.inputMessage.$touched);
    }

}