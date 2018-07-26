import {AppState} from "./app/app.state";
import {MainState} from "./app/main/main.state";
import {LoginState} from "./app/login/login.state";
import {TestState} from "./app/test/test.state";
import {PersonState} from "./app/person/person.state";
import {ServicecallsState} from "./app/servicecalls/servicecalls.state";
import {PersonsState} from "./app/persons/persons.state";
import {ConfigurationItemsState} from "./app/configurationItems/configurationItems.state";
import {QuestionsState} from "./app/questions/questions.state";
import {EntityChangedModal} from "./app/entity-changed/entity-changed.modal";
import {ChangeState} from "./app/change/change.state";
import {ChangeListState} from "./app/change/list/change-list.state";
import {ChangeCardState} from "./app/change/card/change-card.state";
import {ChangeCardViewState} from "./app/change/card/view/change-view.state";
import {ChangeCardHistoryState} from "./app/change/card/history/change-history.state";
import {WorkorderState} from "./app/workorder/workorder.state";
import {WorkorderListState} from "./app/workorder/list/workorder-list.state";
import {WorkorderCardState} from "./app/workorder/card/workorder-card.state";
import {WorkorderCardViewState} from "./app/workorder/card/view/workorder-view.state";
import {WorkorderCardHistoryState} from "./app/workorder/card/history/workorder-history.state";
import {WorkorderCardAttachmentsState} from "./app/workorder/card/attachments/workorder-attachments.state";
import {ChangeCardApprovalState} from "./app/change/card/approval/change-approval.state";
import {ChangeCardAttachmentsState} from "./app/change/card/attachments/change-attachments.state";
import {ChangePasswordState} from "./app/change-password/change-password.state";
import {LeavePageModal} from "./app/leave-page/leave-page.modal";
import {ChangeCreateState} from "./app/change/create/change-create.state";
import {ChangeCreateCommonState} from "./app/change/create/common/change-create-common.state";
import {WorkorderCreateState} from "./app/workorder/create/workorder-create.state";
import {WorkorderCreateCommonState} from "./app/workorder/create/common/workorder-create-common.state";
import {ChangeCardWorkordersState} from "./app/change/card/workorders/change-card-workorders.state";
import {ChangeCreatedModal} from "./app/entity-created/change-created/change-created.modal";
import {WorkorderCreatedModal} from "./app/entity-created/workorder-created/workorder-created.modal";
import {MakeVoteModal} from "./app/make-vote/make-vote.modal";
import {AttachWorkorderModal} from "./app/change/card/workorders/attach-workorder/attach-workorder.modal";
import {ConnectorErrorModal} from "./app/errors/connector-error/connector-error.modal";
import {UIDisplayedErrorModal} from "./app/errors/ui-displayed-error/ui-displayed-error.modal";
import {ProblemState} from "./app/problem/problem.state";
import {ProblemListState} from "./app/problem/list/problem-list.state";
import {ProblemCardState} from "./app/problem/card/problem-card.state";
import {ProblemCardViewState} from "./app/problem/card/view/problem-view.state";

FormConfig.$inject = ["$stateProvider", "$urlRouterProvider", "ModalActionProvider"];
function FormConfig($stateProvider, $urlRouterProvider, ModalActionProvider) {
    $urlRouterProvider.otherwise("/");
    ModalActionProvider
        .modal(EntityChangedModal)
        .modal(ChangeCreatedModal)
        .modal(WorkorderCreatedModal)
        .modal(LeavePageModal)
        .modal(MakeVoteModal)
        .modal(AttachWorkorderModal)
        .modal(ConnectorErrorModal)
        .modal(UIDisplayedErrorModal);
    $stateProvider
        .state(AppState)
        .state(MainState)
        .state(LoginState)
        .state(ChangePasswordState)
        .state(ServicecallsState)
        .state(PersonsState)
        .state(ConfigurationItemsState)
        .state(QuestionsState)
        .state(PersonState)
        .state(ChangeState)
        .state(ChangeListState)
        .state(ChangeCreateState)
        .state(ChangeCreateCommonState)
        .state(ChangeCardState)
        .state(ChangeCardViewState)
        .state(ChangeCardHistoryState)
        .state(ChangeCardApprovalState)
        .state(ChangeCardAttachmentsState)
        .state(ChangeCardWorkordersState)
        .state(WorkorderState)
        .state(WorkorderListState)
        .state(WorkorderCreateState)
        .state(WorkorderCreateCommonState)
        .state(WorkorderCardState)
        .state(WorkorderCardViewState)
        .state(WorkorderCardHistoryState)
        .state(WorkorderCardAttachmentsState)
        .state(ProblemState)
        .state(ProblemListState)
        .state(ProblemCardState)
        .state(ProblemCardViewState)
        .state(TestState)

}

export {FormConfig};