import {NGInject, NGInjectClass} from "../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class AppController {
    @NGInject() Session;
    @NGInject() $scope;
    @NGInject() $state;

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

    get canSeeTabChanges() {
        // return false;
        return this.Session.getTypeAccessRules("Change").isReadEntityAllowed
    }
    get canSeeTabWorkorders() {
        return true;
        // return this.Session.getTypeAccessRules("Workorder").isReadEntityAllowed
    }
    get canSeeTabPersons() {
        return true;
        // return this.Session.getTypeAccessRules("Person").isReadEntityAllowed
    }
}

export {AppController}