import {AppState} from "./app/app.state";
import {MainState} from "./app/main/main.state";
import {LoginState} from "./app/login/login.state";
import {ChangePasswordModal} from "./change-password/change-password.modal";

FormConfig.$inject = ["$stateProvider", "$urlRouterProvider", "ModalActionProvider"];
function FormConfig($stateProvider, $urlRouterProvider, ModalActionProvider) {
    $urlRouterProvider.otherwise("/");
    ModalActionProvider
        .modal(ChangePasswordModal);
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(LoginState)
}

export {FormConfig};