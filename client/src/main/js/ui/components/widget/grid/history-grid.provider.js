const SHORT_DATE_FORMAT = "DD.MM.YYYY (hh:mm)";


HistoryGridProvider.$inject = ["AbstractGrid"];
function HistoryGridProvider(AbstractGrid) {
    /**
     * Таблица изменений
     * @class
     * @name $grid.HistoryGrid
     * @extends $grid.AbstractGrid
     */
    return class HistoryGrid extends AbstractGrid {

        constructor($scope,SD) {
            super($scope, SD.HistoryLine);
            this.columnDefs = [
                { field: 'priority', name: "Приоритет", type: 'string', width: 100, cellTemplate: 'template.grid.cell.priority'},
                { field: 'no', name: "Номер", width: 100, cellClass: "text-right"},
                { field: 'status', name: "Статус", width: 100, cellClass: "text-center"},
                { field: 'subject', name: "Тема", cellTooltip: true, minWidth: 300,
                    cellTemplate: '<a data-ui-sref="app.change.card.view({changeId: row.entity.id})"><span class="ui-grid-cell-contents" style="display: block; width: 100%">{{grid.getCellValue(row, col)}}</span></a>'},
                { field: 'createdDate', name: "Дата создания", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
                { field: 'deadline', name: "Крайний срок", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
                { field: 'resolveDate', name: "Фактически выполнено", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 170, cellClass: "text-center"},
                { field: 'initiator', name: "Инициатор", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150},
                { field: 'manager', name: "Менеджер", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150}
            ];
        }

        gridName = "grid.changes";
        enableSaving = true;
    }

}

export {HistoryGridProvider}