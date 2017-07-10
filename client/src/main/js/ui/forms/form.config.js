import {AppState} from "./app/app.state";
import {MainState} from "./app/main/main.state";
import {LoginState} from "./app/login/login.state";
import {ChangesState} from "./app/changes/changes.state";
import {TestState} from "./app/test/test.state";
import {PersonState} from "./app/person/person.state";
import {ChangePasswordModal} from "./app/change-password/change-password.modal";

FormConfig.$inject = ["$stateProvider", "$urlRouterProvider", "ModalActionProvider"];
function FormConfig($stateProvider, $urlRouterProvider, ModalActionProvider) {
    $urlRouterProvider.otherwise("/");
    ModalActionProvider
        .modal(ChangePasswordModal);
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(LoginState)
        .state(ChangesState)
        .state(PersonState)
        .state(TestState)
}

export {FormConfig};