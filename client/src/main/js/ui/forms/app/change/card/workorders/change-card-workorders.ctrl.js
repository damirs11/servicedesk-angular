import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ChangeCardWorkordersController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() $state;
    @NGInject() $grid;
    /**
     * Промис загрузки данных
     */
    fetchPromise;

    $onInit(){
        this.change = new this.SD.Change(this.changeId);
        this.grid = new this.$grid.WorkorderGrid(this.$scope,this.SD,"grid.workorders.attachedToChange");
        this.gridSearchParams.add({"change":this.changeId});
        this.$scope.$on("grid:fetch",::this.$onTableFetch);
        this.grid.fetchData();
    }

    $onTableFetch(ignored,event) {
        console.log("test");
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise
    }

    get gridSearchParams(){
        return this.grid.searchParamsContainer
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
        this.$state.go("app.workorder.create.common", {changeId: this.changeId});
    }
}

export {ChangeCardWorkordersController as controller}