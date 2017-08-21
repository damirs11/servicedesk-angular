class ChangeCardHistoryController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","changeId"];
    constructor($scope,SD,changeId){
        this.$scope = $scope;
        this.SD = SD;
        this.changeId = changeId;
    }

    $onInit() {
        this.change = new this.SD.Change(this.changeId);
        // this.loadStatuses();
    }
}

export {ChangeCardHistoryController as controller}