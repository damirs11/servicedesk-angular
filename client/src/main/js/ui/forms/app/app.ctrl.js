class AppController {
    static $inject = ['SD', '$scope', 'ModalAction', '$state', '$transitions'];
    constructor(SD, $scope, ModalAction, $state, $transitions){
        //this.appSessionService = appSessionService;
        this.SD = SD;
        this.ModalAction = ModalAction;
        this.$scope = $scope;
        this.$state = $state;

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

    loginClick(){
        this.$state.go("app.login")
    }

    async passwordChange() {
        try {
            await this.ModalAction.changePassword(this.$scope)
        } catch (ignored) {}
    }
}

export {AppController}