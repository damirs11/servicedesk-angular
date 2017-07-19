class AppController {
    static $inject = ['Session', '$scope', 'ModalAction', '$state'];
    constructor(Session, $scope, ModalAction, $state){
        this.Session = Session;
        this.ModalAction = ModalAction;
        this.$scope = $scope;
        this.$state = $state;

    }

    get user(){
        return this.Session.user;
    }

    get authorized(){
        return this.Session.authorized;
    }

    async logout() {
        await this.Session.logout();
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