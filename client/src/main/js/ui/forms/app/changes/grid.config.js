import {AbstractGrid} from "../../../components/widget/grid/abstract-grid";

class ChangesGridOptions extends AbstractGrid {

    constructor($scope, $connector, entityClass) {
        super($scope, $connector, entityClass);
        this.columnDefs = [
            { field: 'no', name: "Номер"},
            { field: 'status', name: "Статус"},
            { field: 'subject', name: "Тема"},
            { field: 'createdDate', name: "Дата создания", type: 'date', cellFilter: 'date:"short"'},
            { field: 'deadline', name: "Крайний срок", type: 'date', cellFilter: 'date'},
            { field: 'resolveDate', name: "Фактически выполнено", type: 'date', cellFilter: 'date:"dd.MM.yyyy hh:mm"'},
            { field: 'priority', name: "Приоритет", type: 'object', cellTemplate: '<span>{{row.entity[column.field].name}}</span>'},
            { field: 'initiator', name: "Инициатор", type: 'object', cellTemplate: '<span>{{row.entity[column.field].shortName}}</span>' },
            { field: 'manager', name: "Менеджер", type: 'object', cellTemplate: '<span>{{row.entity[column.field].shortName}}</span>' }
        ];
    }
}

export {ChangesGridOptions};