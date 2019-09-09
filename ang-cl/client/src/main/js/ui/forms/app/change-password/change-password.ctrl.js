import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangePasswordController {

    @NGInject() $scope;
    @NGInject() Session;
    @NGInject() $state;

    constructor() {
        this.changeFailed = false;
        this.minLength = 6; // минимальная длина пароля
        this.maxLength = 50; // максимальная длина пароля
        this.oldPassword = null; // прежний пароль
        this.newPassword = null; // новый пароль
        this.confirmPassword = null; // повторно введенный новый пароль
    }

    async save() {
        if (this.$scope.passwordChangeForm.$invalid) {
            return;
        }
        try {
            await this.Session.changePassword(this.oldPassword, this.newPassword)
        } catch (errorResponse) {
            this.changeFailed = true;
            return;
        }
        this.Session.logout();
        this.$state.go("app.login");
    }

}

export {ChangePasswordController as controller}