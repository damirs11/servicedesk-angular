import {NGInject, NGInjectClass} from "../../../../../../common/decorator/ng-inject.decorator";

const ERROR_HISTORY_READ_DISALLOWED = Symbol("ERROR_HISTORY_READ_DISALLOWED");

@NGInjectClass()
class ChangeCardHistoryController{
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
        const change = this.change = new this.SD.Change(this.changeId);
        this.accessRules = change.accessRules;
        if (!this.accessRules.isReadHistoryAllowed) return;
        const grid = this.grid = new this.$grid.HistoryGrid(this.$scope,this.SD,change);
        this.$scope.$on("grid:fetch",::this.$onTableFetch);
        grid.fetchData();
    }


    $onTableFetch(ignored,event) {
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise
    }
}

export {ChangeCardHistoryController as controller}