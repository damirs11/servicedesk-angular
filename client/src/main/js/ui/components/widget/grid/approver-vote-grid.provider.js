import rowTemplate from "./row.html";

const SHORT_DATE_FORMAT = "DD.MM.YYYY (hh:mm)";

ApproverVoteGridProvider.$inject = ["AbstractGrid"];
function ApproverVoteGridProvider(AbstractGrid) {
    /**
     * Таблица изменений
     * @class
     * @name $grid.ApproverVoteGrid
     * @extends $grid.AbstractGrid
     */
    return class ApproverVoteGrid {

        /**
         * @param $scope - скоуп, в котором будет работать таблица.
         * @param SD - объект SD, необходим для использования того же кэша, что в контроллере
         * @param entity - сущность, у которой берутся голоса
         */
        constructor($scope,SD,entity) {
            this.$scope = $scope;
            this.SD = SD;
            this.entity = entity;
            this.columnDefs = [
                { field: 'approved', name: "Голос", width: 150, type: 'string', cellClass: "text-center", cellTemplate: 'template.grid.cell.approved', enableHiding: false},
                { field: 'approver', name: "Персона", width: 150, cellTemplate: 'template.grid.cell.person', enableHiding: false},
                { field: 'reason', name: "Причина", type: 'string', cellTooltip: true, minWidth: 260, enableHiding: false},
            ];

            this.enableHorizontalScrollbar = 2; // скороллбар в бок
            this.enableVerticalScrollbar = 2; // ставим скроллбар наверз "WHEN_NEEDED"

            this.enableRowSelection = false; // Дополнительный столбец для выбора строк таблицы
            this.enableSorting = false; // Выключаем сортировку
            this.enableSelectAll = false; // Галочка в заголовке дополнительного столбца
            this.rowHeight = 25;
            this.rowTemplate = rowTemplate;

            this.autoResize =true;
        }

        onRegisterApi(gridApi) {
            this.gridApi = gridApi;
            this.grid = gridApi.grid;
        }

        /**
         * Получение данных для выбранной страницы таблицы. Вызывается каждый раз, когда меняются условия
         * отображения данных: смена сортировки, переход на другую страницу, смена условий фильтрации данных.
         */
        async fetchData() {
            this.data = await this.entity.getApproverVotes()
        }

        /**
         * Формирование параметров запроса: пейджинг, сортировка + пользовательские фильтры
         */

    }
}

export {ApproverVoteGridProvider}