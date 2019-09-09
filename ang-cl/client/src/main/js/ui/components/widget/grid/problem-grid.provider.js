const SHORT_DATE_FORMAT = "DD.MM.YYYY (HH:mm)";


ProblemGridProvider.$inject = ["AbstractGrid"];
function ProblemGridProvider(AbstractGrid) {
    /**
     * Таблица проблем
     * @class
     * @name $grid.ProblemGrid
     * @extends $grid.AbstractGrid
     */
    return class ProblemGrid extends AbstractGrid {

        constructor($scope,SD,options) {
            super($scope, SD.Problem);
            if (options) {
                if (options.gridName) this.gridName = options.gridName;
            }
            this.columnDefs = [
                { field: 'priority', name: "Приоритет", type: 'string', width: 100, cellTemplate: 'template.grid.cell.priority'},
                { field: 'no', name: "Номер", width: 100, cellClass: "text-right"},
                { field: 'status', name: "Статус", width: 100, cellClass: "text-center"},
                { field: 'subject', name: "Тема", cellTooltip: true, minWidth: 300,
                    cellTemplate: '<a data-ui-sref="app.problem.card.view({problemId: row.entity.id})"><span class="ui-grid-cell-contents" style="display: block; width: 100%">{{grid.getCellValue(row, col)}}</span></a>'},
                { field: 'deadline', name: "Крайний срок", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150, cellClass: "text-center"},
                { field: 'initiator', name: "Инициатор", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150},
                { field: 'assignment.executor', name: "Исполнитель", type: 'string', cellTemplate: 'template.grid.cell.person', width: 150}
            ];
        }

        gridName = "grid.problems";
        enableSaving = true;
    }

}


export {ProblemGridProvider}