import {AbstractGrid} from "../../../../components/widget/grid/abstract-grid";

const SHORT_DATE_FORMAT = "DD.MM.YYYY (hh:mm)";

class ChangesGridOptions extends AbstractGrid {

    constructor($scope, $connector, entityClass, $state, $parse) {
        super($scope, $connector, entityClass, $parse);
        this.$state = $state;
        this.columnDefs = [
            { field: 'priority', name: "Приоритет", type: 'string', width: 100, cellTemplate: 'template.grid.cell.priority'},
            { field: 'no', name: "Номер", width: 100, cellClass: "text-right"},
            { field: 'status', name: "Статус", width: 100, cellClass: "text-center"},
            { field: 'subject', name: "Тема", cellTooltip: true, minWidth: 300},
            { field: 'createdDate', name: "Дата создания", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
            { field: 'deadline', name: "Крайний срок", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
            { field: 'resolveDate', name: "Фактически выполнено", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 170, cellClass: "text-center"},
            { field: 'initiator', name: "Инициатор", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150},
            { field: 'manager', name: "Менеджер", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150}
        ];
        $scope._onDblClick = ::this.openChange
    }

    openChange(row){
        this.$state.go("app.change.card.view", {changeId: row.entity.id});
    }
}

export {ChangesGridOptions};