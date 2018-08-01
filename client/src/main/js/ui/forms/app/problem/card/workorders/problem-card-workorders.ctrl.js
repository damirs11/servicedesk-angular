import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ProblemCardWorkordersController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() problemId;
    @NGInject() $state;
    @NGInject() $grid;
    @NGInject() ModalAction;
    /**
     * Промис загрузки данных
     */
    fetchPromise;

    $onInit(){
        this.problem = new this.SD.Problem(this.problemId);
        this.grid = new this.$grid.WorkorderGrid(this.$scope,this.SD,"grid.workorders.attachedToProblem");
        this.gridSearchParams.add({"problem":this.change.id});
        this.$scope.$on("grid:fetch",::this.$onTableFetch);
        this.grid.fetchData();
    }

    $onTableFetch(ignored,event) {
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise
    }

    get gridSearchParams(){
        return this.grid.searchParamsContainer
    }

    async clickAttach(){
        await this.ModalAction.attachWorkorder(this.$scope, {entity: this.problem, workorderField: "problem"});
        this.grid.fetchData();
    }

    clickOpen(){
        const rows = this.grid.getSelectedRows();
        if (rows.length != 1) return;
        const row = rows[0];
        this.$state.go("app.workorder.card.view", {workorderId: row.id});
    }

    get isButtonOpenDisabled() {
        return this.grid.getSelectedRows().length != 1;
    }

    clickCreateWorkorder(){
        this.$state.go("app.workorder.create.common", {problemId: this.problemId});
    }
}

export {ProblemCardWorkordersController as controller}