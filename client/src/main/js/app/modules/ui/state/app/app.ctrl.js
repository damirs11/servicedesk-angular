class AppController {
    static $inject = ['SD', '$scope', 'ModalAction'];

    constructor(SD, $scope, ModalAction){
        //this.appSessionService = appSessionService;
        this.SD = SD;
        ModalAction.changePassword($scope)
    }

    get user(){
        return this.SD.user;
    }

    get authorized(){
        return this.SD.authorized;
    }

    logout() {
        this.SD.logout().catch(e => {
            alert("Не удалось выйти");
        });
    }

    passwordChange() {
        this.uiDialogs.create('js/app/passwordChange/passwordChange.html', 'appPasswordChangeController');
    }
}

export {AppController}