import {AppState} from "./app/app.state";
import {MainState} from "./app/main/main.state";
import {LoginState} from "./app/login/login.state";
import {TestState} from "./app/test/test.state";
import {ChangePasswordModal} from "./app/change-password/change-password.modal";

FormConfig.$inject = ["$stateProvider", "$urlRouterProvider", "ModalActionProvider"];
function FormConfig($stateProvider, $urlRouterProvider, ModalActionProvider) {
    $urlRouterProvider.otherwise("/");
    ModalActionProvider
        .modal(ChangePasswordModal);
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(TestState)
        .state(LoginState)
}

export {FormConfig};