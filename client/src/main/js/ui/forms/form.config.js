import {AppState} from "./app/app.state";
import {MainState} from "./app/main/main.state";
import {LoginState} from "./app/login/login.state";
import {TestState} from "./app/test/test.state";
import {PersonState} from "./app/person/person.state";
import {ChangePasswordModal} from "./app/change-password/change-password.modal";
import {ServicecallsState} from "./app/servicecalls/servicecalls.state";
import {WorkordersState} from "./app/workorders/workorders.state";
import {PersonsState} from "./app/persons/persons.state";
import {ConfigurationItemsState} from "./app/configurationItems/configurationItems.state";
import {QuestionsState} from "./app/questions/questions.state";
import {EntityChangedModal} from "./app/entity-changed/entity-changed.modal";
import {ChangeState} from "./app/change/change.state";
import {ChangeListState} from "./app/change/list/change-list.state";
import {ChangeCardState} from "./app/change/card/change-card.state";
import {ChangeCardViewState} from "./app/change/card/view/change-view.state";

FormConfig.$inject = ["$stateProvider", "$urlRouterProvider", "ModalActionProvider"];
function FormConfig($stateProvider, $urlRouterProvider, ModalActionProvider) {
    $urlRouterProvider.otherwise("/");
    ModalActionProvider
        .modal(ChangePasswordModal)
        .modal(EntityChangedModal);
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(LoginState)
        .state(ServicecallsState)
        .state(WorkordersState)
        .state(PersonsState)
        .state(ConfigurationItemsState)
        .state(QuestionsState)
        .state(PersonState)
        .state(ChangeState)
        .state(ChangeListState)
        .state(ChangeCardState)
        .state(ChangeCardViewState)
        .state(TestState)

}

export {FormConfig};