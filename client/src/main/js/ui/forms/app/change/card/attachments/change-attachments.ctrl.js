class ChangeCardAttachmentsController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","changeId"];
    constructor($scope, SD, changeId, $grid){
        this.$scope = $scope;
        this.SD = SD;
        this.changeId = changeId;
    }

    $onInit(){
        this.change = this.SD.Change.parse(this.changeId)
    }
}

export {ChangeCardAttachmentsController as controller}