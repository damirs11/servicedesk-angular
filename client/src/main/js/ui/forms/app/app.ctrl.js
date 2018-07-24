import {NGInject, NGInjectClass} from "../../../common/decorator/ng-inject.decorator";
import {UIDisplayedError} from "../../utils/ui-displayed-error";

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

    /**
     * Право на просмотр "Изменений"
     * @returns {Boolean}
     */
    get canSeeTabChanges() {
        return this.Session.getTypeAccessRules("Change") && this.Session.getTypeAccessRules("Change").isReadEntityAllowed;
    }
    /**
     * Право на просмотр "Заявок"
     * @returns {Boolean}
     */
    get canSeeTabServiceCalls() {
        return false; // не реализовано
        // return this.Session.getTypeAccessRules("ServiceCall").isReadEntityAllowed
    }
    /**
     * Право на просмотр "Нарядов"
     * @returns {Boolean}
     */
    get canSeeTabWorkorders() {
        return this.Session.getTypeAccessRules("Workorder") && this.Session.getTypeAccessRules("Workorder").isReadEntityAllowed;
    }
    /**
     * Право на просмотр "Персон"
     * @returns {Boolean}
     */
    get canSeeTabPersons() {
        return false; // временно скроем
        // return this.Session.getTypeAccessRules("Person").isReadEntityAllowed
    }
    /**
     * Право на просмотр "Объектов" (обслуживания)
     * @returns {Boolean}
     */
    get canSeeTabConfigurationItems() {
        return false; // временно скроем
        // return this.Session.getTypeAccessRules("ConfigurationItem").isReadEntityAllowed
    }
    /**
     * Право на просмотр "Изменений"
     * @returns {Boolean}
     */
    get canSeeTabProblems() {
        return true; // Не реализован доступ к правам
    }
}

export {AppController}