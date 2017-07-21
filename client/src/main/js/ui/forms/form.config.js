import {AppState} from "./app/app.state";
import {MainState} from "./app/main/main.state";
import {LoginState} from "./app/login/login.state";
import {ChangesState} from "./app/changes/changes.state";
import {TestState} from "./app/test/test.state";
import {PersonState} from "./app/person/person.state";
import {ChangePasswordModal} from "./app/change-password/change-password.modal";
import {ServicecallsState} from "./app/servicecalls/servicecalls.state";
import {WorkordersState} from "./app/workorders/workorders.state";
import {PersonsState} from "./app/persons/persons.state";
import {ConfigurationItemsState} from "./app/configurationItems/configurationItems.state";
import {QuestionsState} from "./app/questions/questions.state";
import {ChangeState} from "./app/change/change.state";

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
        .state(ServicecallsState)
        .state(WorkordersState)
        .state(PersonsState)
        .state(ConfigurationItemsState)
        .state(QuestionsState)
        .state(PersonState)
        .state(ChangeState)
        .state(TestState)
}

export {FormConfig};