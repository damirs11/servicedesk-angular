const SHORT_DATE_FORMAT = "DD.MM.YYYY (hh:mm)";


WorkorderGridProvider.$inject = ["AbstractGrid"];
function WorkorderGridProvider(AbstractGrid) {
    /**
     * Таблица изменений
     * @class
     * @name $grid.WorkorderGrid
     * @extends $grid.AbstractGrid
     */
    return class WorkorderGrid extends AbstractGrid {

        constructor($scope,SD) {
            super($scope, SD.Workorder);
            this.columnDefs = [
                { field: 'priority', name: "Приоритет", type: 'string', width: 100, cellTemplate: 'template.grid.cell.priority'},
                { field: 'no', name: "Номер", width: 100, cellClass: "text-right"},
                { field: 'status', name: "Статус", width: 100, cellClass: "text-center"},
                { field: 'expired', name: "Просрочен", width: 100, cellTemplate: 'template.grid.cell.expired'},
                { field: 'subject', name: "Тема", cellTooltip: true, minWidth: 300,
                    cellTemplate: '<a data-ui-sref="app.workorder.card.view({workorderId: row.entity.id})"><span class="ui-grid-cell-contents" style="display: block; width: 100%">{{grid.getCellValue(row, col)}}</span></a>'},
                { field: 'createdDate', name: "Дата создания", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
                { field: 'deadline', name: "Крайний срок", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
                { field: 'resolveDate', name: "Фактически выполнено", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 170, cellClass: "text-center"},
                { field: 'initiator', name: "Инициатор", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150},
                { field: 'assigneePerson', name: "Исполнитель", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150}
            ];
        }

        gridName = "grid.workorders";
        enableSaving = true;
    }

}

export {WorkorderGridProvider}