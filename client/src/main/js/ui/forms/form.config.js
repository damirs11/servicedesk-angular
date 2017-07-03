import {AppState} from "app/app.state";
import {MainState} from "app/main/main.state";
import {LoginState} from "app/login/login.state";
import {UserState} from "app/user/user.state";
import {ChangePassword} from "modal/change-password/change-password.modal";

StateConfig.$inject = ["$stateProvider", "$urlRouterProvider", "ModalActionProvider"];
function StateConfig($stateProvider, $urlRouterProvider, ModalActionProvider) {
    $urlRouterProvider.otherwise("/");
    ModalActionProvider
        .modal(ChangePassword);
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(LoginState)
        .state(UserState);

}

export {StateConfig};