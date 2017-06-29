export class ChangePasswordController{

    static $inject = ["$scope","ModalAction","$modalState"];

    constructor($scope,ModalAction,$modalState){
        console.log("Modal 1");
        //ModalAction.test($scope);
        this.$modalState = $modalState;
        this.loginFailed = false;
        this.minLength = 6; // минимальная длина пароля
        this.maxLength = 50; // максимальная длина пароля
        this.oldPassword = null; // прежний пароль
        this.newPassword = null; // новый пароль
        this.confirmPassword = null; // повторно введенный новый пароль
    }

    close(){
        console.log("closing");
        this.$modalState.resolve()
    }
}