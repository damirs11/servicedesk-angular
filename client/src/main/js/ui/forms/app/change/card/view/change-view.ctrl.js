import {CHANGE_MESSAGE_TYPES} from "../../../../../components/widget/sd-entity-chat/chat-types";

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
        this.msgTypes = CHANGE_MESSAGE_TYPES;
    }

    $onInit() {
        this.change = new this.SD.Change(this.changeId);
        this.loadStatuses();
    }

    async loadStatuses() {
        const statuses = await this.SD.EntityStatus.list();
        this.statusList = statuses.filter(status => status['entityType'] === "CHANGE"); // Временный фильтр. Уберу, когда появится поиск по статусам

    }

    async createTestChange() {
        const parent = this.change;
        const testChange = new this.SD.Change();
        testChange.category = parent.category;
        testChange.classification = parent.classification;
        testChange.description = "Проверка создания изменений";
        testChange.subject = "Тестовое изменение";
        testChange.priority = parent.priority;
        testChange.deadline = new Date(1504627527522);
        testChange.initiator = parent.manager;
        testChange.manager = parent.initiator;
        testChange.executor = parent.manager;
        await testChange.create();
    }
}

export {ChangeCardViewController as controller}