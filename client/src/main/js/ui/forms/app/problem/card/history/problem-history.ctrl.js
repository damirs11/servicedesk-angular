import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

const ERROR_HISTORY_READ_DISALLOWED = Symbol("ERROR_HISTORY_READ_DISALLOWED");

@NGInjectClass()
class ProblemCardHistoryController{
    @NGInject() $scope;
    @NGInject() SD;
    @NGInject() changeId;
    @NGInject() $grid;
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";
    /**
     * Промис загрузки данных
     */
    fetchPromise;


    async $onInit() {
        const history = this.history = new this.SD.Problem(this.historyId);
        this.accessRules = history.accessRules;
        if (!this.accessRules.isReadHistoryAllowed) return;
        const grid = this.grid = new this.$grid.HistoryGrid(this.$scope,this.SD,history);
        this.$scope.$on("grid:fetch",::this.$onTableFetch);
        grid.fetchData();
    }


    $onTableFetch(ignored,event) {
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise
    }
}

export {ProblemCardHistoryController as controller}