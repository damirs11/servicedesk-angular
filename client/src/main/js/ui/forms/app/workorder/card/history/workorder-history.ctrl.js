class WorkorderCardHistoryController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","workorderId","$grid"];
    constructor($scope, SD, workorderId, $grid){
        this.$scope = $scope;
        this.SD = SD;
        this.workorderId = workorderId;
        this.$grid = $grid;
    }

    $onInit() {
        const workorder = this.workorder = new this.SD.Workorder(this.workorderId);
        const grid = this.grid = new this.$grid.HistoryGrid(this.$scope,this.SD,workorder);
        grid.fetchData();
    }
}

export {WorkorderCardHistoryController as controller}