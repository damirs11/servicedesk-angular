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

    async logout() {
        await this.SD.logout();
        this.$state.go("app.main")
    }

    loginClick(){
        this.$state.go("app.login")
    }

    async passwordChange() {
        await this.ModalAction.changePassword(this.$scope)
    }
}

export {AppController}