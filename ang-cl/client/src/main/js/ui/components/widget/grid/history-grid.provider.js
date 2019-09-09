import rowTemplate from "./row.html";

const SHORT_DATE_FORMAT = "DD.MM.YYYY (HH:mm)";

HistoryGridProvider.$inject = ["AbstractGrid"];
function HistoryGridProvider(AbstractGrid) {
    /**
     * Таблица изменений
     * @class
     * @name $grid.HistoryGrid
     * @extends $grid.AbstractGrid
     */
    return class HistoryGrid {

        /**
         * @param $scope - скоуп, в котором будет работать таблица.
         * @param SD - объект SD, необходим для использования того же кэша, что в контроллере
         * @param entity - сущность, у которой берется история
         */
        constructor($scope,SD,entity) {
            this.$scope = $scope;
            this.SD = SD;
            this.entity = entity;
            this.columnDefs = [
                { field: 'date', name: "Дата", type: 'date', cellFilter: `amDateFormat:"${SHORT_DATE_FORMAT}"`, width: 150,
                    cellClass: "text-center", sort: {direction:"desc"}, enableHiding: false},
                { field: 'subject', name: "Описание", cellTooltip: true, minWidth: 300, enableHiding: false},
                { field: 'account', name: "Пользователь", type: 'string', width: 260, enableHiding: false},
            ];

            this.enableHorizontalScrollbar = 2; // скороллбар в бок
            this.enableVerticalScrollbar = 2; // ставим скроллбар наверз "WHEN_NEEDED"

            this.enableRowSelection = false; // Дополнительный столбец для выбора строк таблицы
            this.enableSelectAll = false; // Галочка в заголовке дополнительного столбца
            this.rowHeight = 20;
            this.paginationPageSizes = [20, 50, 100, 200, 500, 1000];
            this.paginationPageSize = 20;
            this.paginationCurrentPage = 1;
            this.useExternalPagination = true;
            this.useExternalSorting = true;
            this.rowTemplate = rowTemplate;
            this.autoResize =true;

            this.sort = [this.columnDefs[0]];
        }

        onRegisterApi(gridApi) {
            this.gridApi = gridApi;
            this.grid = gridApi.grid;

            gridApi.core.on.sortChanged(this.$scope, this._onSortChanged);
            gridApi.pagination.on.paginationChanged(this.$scope, this._onPaginationChanged);
        }

        /**
         * Получение данных для выбранной страницы таблицы. Вызывается каждый раз, когда меняются условия
         * отображения данных: смена сортировки, переход на другую страницу, смена условий фильтрации данных.
         */
        async fetchData() {
            let params = this._getRequestParams();
            // Получение данных. Одновременная отправка двух запросов
            const fetchPromise = Promise.all([
                this.entity.getHistoryCount(params),
                this.entity.getHistory(params)
            ]);
            this._broadcastEvent("grid:fetch",{params,fetchPromise});
            const result = await fetchPromise;
            // Общее количество
            this.totalItems = result[0];
            // Данные строк таблицы
            let rows = [];
            let limit = this.paginationPageSize;
            result[1].every(obj => {
                rows.push(obj);
                return rows.length < limit;
            });
            this.data = rows;
        }

        /**
         * Формирование параметров запроса: пейджинг, сортировка + пользовательские фильтры
         */
        _getRequestParams(params) {
            params = params || {};
            // Параметры пейджинга
            let from = this.paginationPageSize * (this.paginationCurrentPage - 1) + 1;
            let to = from + this.paginationPageSize - 1;
            params.paging = from + ';' + to;
            // Параметры сортировки
            let sortColumns = this.sort;
            if (sortColumns && sortColumns.length > 0) {
                let sort = '';
                sortColumns.forEach(function (column) {
                    sort += column.field + '-' + column.sort.direction + ';'
                });
                params.sort = sort
            }
            return params;
        };

        /**
         * Обработчик смены условий сортировки данных
         */
        _onSortChanged(grid, sortColumns) {
            console.log(sortColumns);
            this.sort = sortColumns;
            this._broadcastEvent("grid:sort-changed",{sortColumns});
            this.fetchData();
        };

        /**
         * Вызывается при смене номера текущей страницы, либо изменения количества отображаемых записей страницы
         */
        _onPaginationChanged(pageNumber, pageSize) {
            this._broadcastEvent("grid:pagination-changed",{pageNumber,pageSize});
            this.fetchData();
        };

        /**
         * Пушит событие
         * @private
         */
        _broadcastEvent(name,data){
            data.grid = this;
            this.$scope.$broadcast(name,data)
        }
    }
}

export {HistoryGridProvider}