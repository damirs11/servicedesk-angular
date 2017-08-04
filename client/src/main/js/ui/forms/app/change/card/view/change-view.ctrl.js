class ChangeCardViewController{
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
        this.loadStatuses();
    }

    async loadStatuses() {
        const statuses = await this.SD.EntityStatus.list();
        this.statusList = statuses.filter(status => status['entityType'] === "CHANGE"); // Временный фильтр. Уберу, когда появится поиск по статусам

    }
}

export {ChangeCardViewController as controller}