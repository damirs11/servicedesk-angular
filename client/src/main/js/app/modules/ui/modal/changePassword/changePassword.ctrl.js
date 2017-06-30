export class ChangePasswordController{

    static $inject = ["$scope","SD","$modalState"];

    constructor($scope,SD,$modalState){
        this.$modalState = $modalState;
        this.SD = SD;
        this.$scope = $scope;

        this.loginFailed = false;
        this.minLength = 6; // минимальная длина пароля
        this.maxLength = 50; // максимальная длина пароля
        this.oldPassword = null; // прежний пароль
        this.newPassword = null; // новый пароль
        this.confirmPassword = null; // повторно введенный новый пароль
    }

    close(){
        this.$modalState.reject(123)
    }

    async save() {
        if (this.$scope.passwordChangeForm.$invalid) {
            return;
        }
        try {
            await this.SD.changePassword(this.oldPassword, this.newPassword)
        } catch (errorResponse) {
            this.loginFailed = true;
            return
        }
        this.$modalState.resolve();
    }

}