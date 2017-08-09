import {AbstractGrid} from "../../../../components/widget/grid/abstract-grid";

const SHORT_DATE_FORMAT = "DD.MM.YYYY (hh:mm)";

class ChangesGridOptions extends AbstractGrid {

    constructor($scope, $connector, entityClass, $state, $parse) {
        super($scope, $connector, entityClass, $parse);
        this.$state = $state;
        this.columnDefs = [
            { field: 'no', name: "Номер"},
            { field: 'status', name: "Статус"},
            { field: 'subject', name: "Тема", cellTooltip: true},
            { field: 'createdDate', name: "Дата создания", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`},
            { field: 'deadline', name: "Крайний срок", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`},
            { field: 'resolveDate', name: "Фактически выполнено", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`},
            { field: 'priority', name: "Приоритет", type: 'object', cellTemplate: '<span style="background-color: red">{{row.entity[column.field].name}}</span>'},
            { field: 'initiator', name: "Инициатор", type: 'object', cellTemplate: '<span>{{row.entity[column.field].shortName}}</span>' },
            { field: 'manager', name: "Менеджер", type: 'object', cellTemplate: '<span>{{row.entity[column.field].shortName}}</span>' }
        ];
        $scope._onDblClick = ::this.openChange
    }

    openChange(row){
        this.$state.go("app.change.card.view", {changeId: row.entity.id});
    }
}

export {ChangesGridOptions};