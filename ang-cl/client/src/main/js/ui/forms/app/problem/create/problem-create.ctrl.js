import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";
import {BaseEntityCreate} from "../../entity-created/base-create";

@NGInjectClass()
class ProblemCreateController extends BaseEntityCreate {
    // NG зависимости
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() $state;
    @NGInject() $pageLock;
    @NGInject() ModalAction;
    @NGInject() passedParams;
    @NGInject() problem; // Новое изменение - внедряется зависимостью.

    async $onInit(){
        this.stateCreated = "app.problem.create.common";
        this.stateView = "app.problem.card.view";
        this.stateList = "app.problem.list";
        this.entity = this.problem;
        super.$onInit();
    }

    async _callCreatedFunc() {
        const createdProblem = await this.problem.create();
        await this.ModalAction.problemCreated(this.$scope,{problem:createdProblem});
        this.$state.go(this.stateView, {problemId: createdProblem.id});
    }

}

export {ProblemCreateController as controller}