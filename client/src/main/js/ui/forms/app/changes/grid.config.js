import {AbstractGrid} from "../../../components/widget/grid/abstract-grid";

class ChangesGridOptions extends AbstractGrid {

    constructor($scope, $connector, entityClass) {
        super($scope, $connector, entityClass);
        this.columnDefs = [
            { field: 'no', name: "Номер" },
            { field: 'status', name: "Статус" },
            { field: 'subject', name: "Тема"},
            { field: 'createdDate', name: "Дата создания", type: 'date', cellFilter: 'date : "dd.MM.yyyy hh:mm"', enableFiltering: true},
            { field: 'deadline', name: "Крайний срок", type: 'numberStr', cellFilter: 'date:"dd.MM.yyyy hh:mm"'},
            { field: 'resolveDate', name: "Фактически выполнено", type: 'date', cellFilter: 'date:"dd.MM.yyyy hh:mm"'},
            { field: 'priority', name: "Приоритет", type: 'object'},
            { field: 'initiator', name: "Инициатор", type: 'object' },
            { field: 'manager', name: "Менеджер", type: 'object', cellTemplate: "{{row.entity.firstName}}" }
        ];
    }
}

export {ChangesGridOptions};