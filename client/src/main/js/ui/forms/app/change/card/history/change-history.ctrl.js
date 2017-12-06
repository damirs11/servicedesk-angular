class ChangeCardHistoryController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    fetchPromise;

    static $inject = ["$scope","SD","changeId","$grid"];
    constructor($scope, SD, changeId, $grid){
        this.$scope = $scope;
        this.SD = SD;
        this.changeId = changeId;
        this.$grid = $grid;
    }

    $onInit() {
        const change = this.change = new this.SD.Change(this.changeId);
        const grid = this.grid = new this.$grid.HistoryGrid(this.$scope,this.SD,change);
        this.$scope.$on("grid:fetch",::this.$onTableFetch);
        grid.fetchData();
    }

    $onTableFetch(ignored,event) {
        if (this.fetchPromise != null) return;
        this.fetchPromise = event.fetchPromise;
    }
}

export {ChangeCardHistoryController as controller}