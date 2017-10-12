class ChangeCardApprovalController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","changeId","$grid"];
    constructor($scope, SD, changeId, $grid){
        this.$scope = $scope;
        this.SD = SD;
        this.changeId = changeId;
        this.$grid = $grid;
    }

    $onInit() {
        const change = this.change = new this.SD.Change(this.changeId);
        const grid = this.grid = new this.$grid.ApproverVoteGrid(this.$scope,this.SD,change);
        grid.fetchData();
    }
}

export {ChangeCardApprovalController as controller}