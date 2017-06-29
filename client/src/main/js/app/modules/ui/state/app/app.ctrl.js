class AppController {
    static $inject = ['SD', '$scope', 'ModalAction'];

    constructor(SD, $scope, ModalAction){
        //this.appSessionService = appSessionService;
        this.SD = SD;
        this.ModalAction = ModalAction;
        this.$scope = $scope;
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
        this.ModalAction.changePassword(this.$scope)
    }
}

export {AppController}