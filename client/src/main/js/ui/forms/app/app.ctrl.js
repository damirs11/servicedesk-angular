class AppController {
    static $inject = ['SD', '$scope', 'ModalAction', '$state'];
    constructor(SD, $scope, ModalAction, $state){
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

    async passwordChange() {
        try {
            await this.ModalAction.changePassword(this.$scope)
        } catch (ignored) {}
    }
}

export {AppController}