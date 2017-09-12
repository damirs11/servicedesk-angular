import {WORKORDER_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";

class WorkorderCardViewController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","workorderId"];
    constructor($scope,SD,workorderId){
        this.$scope = $scope;
        this.SD = SD;
        this.workorderId = workorderId;
        this.msgTypes = WORKORDER_MESSAGE_TYPES;
    }

    $onInit() {
        this.workorder = new this.SD.Workorder(this.workorderId); // В кэше он уже лежит, т.к. подгружен стейтом-родителем.
        this.loadStatuses();
    }

    async loadStatuses() {
        const statuses = await this.SD.EntityStatus.list();
        this.statusList = statuses.filter(status => status['entityType'] === "WORKORDER"); // Временный фильтр. Уберу, когда появится поиск по статусам

    }
}

export {WorkorderCardViewController as controller}