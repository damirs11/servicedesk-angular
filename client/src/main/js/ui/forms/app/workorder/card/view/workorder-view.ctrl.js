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

    async createTestWorkorder() {
        const parent = this.workorder;
        const testEntity = new this.SD.Workorder();
        testEntity.description = "Проверка создания нарядов";
        testEntity.subject = "Тестовый наряд";
        testEntity.labor = 12000;
        testEntity.category = parent.category;
        testEntity.status = parent.status;
        testEntity.deadline = new Date(Date.now()+2*60*60*1000);
        testEntity.initiator = parent.initiator;
        testEntity.assigneePerson = parent.assigneePerson;
        testEntity.workgroup = parent.workgroup;
        testEntity.solution = "Решения еще нет.";
        await testEntity.create();
    }
}

export {WorkorderCardViewController as controller}