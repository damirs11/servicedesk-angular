import rowTemplate from "./row.html";

/**
 * Абстрактный класс для работы с таблицой. Содержит базовые настройки и обработчики событий.
 */
class AbstractGrid {

    constructor($scope, $connector, entityClass) {
        if (this.constructor === AbstractGrid) {
            throw new TypeError('Нельзя создавать экземпляры абстрактного класса "AbstractGrid"!');
        }
        if (!$scope) {
            throw new TypeError('Аргумент "$scope" должен быть задан!');
        }
        if (!entityClass) {
            throw new TypeError('Аргумент "entityClass" должен быть задан!');
        }
        this.$scope = $scope;
        this.$connector = $connector;
        this.entityClass = entityClass; // Класс, данные которого будут отображаться в таблице
        this.grid = null; // Выставляем при инициализации таблицы
        this.sort = []; // Параметры сортировки данных

        // Свойства таблицы ui-grid
        this.enableCellEdit = false; // Запрещаем редактирование
        this.enableSorting = true; // Включаем сортировку
        this.excludeSortColumns = true;
        this.enableRowSelection = true; // Дополнительный столбец для выбора строк таблицы
        this.enableSelectAll = true; // Галочка в заголовке дополнительного столбца
        this.selectionRowHeaderWidth = 35;
        this.rowHeight = 30;
        //this.enableFiltering = true; //Включает поле для быстрой фильтрации данных на странице
        this.multiSelect = true;
        this.paginationPageSizes = [20, 50, 100, 200, 500, 1000];
        this.paginationPageSize = 20;
        this.paginationCurrentPage = 1;
        this.useExternalPagination = true;
        this.useExternalSorting = true;
        this.rowTemplate = rowTemplate; // Необходим для расширения функционала работы со строкой таблицы
        this.columnDefs = []; // Описание колонок таблицы
        this.data = []; // Содержимое таблицы
        this.totalItems = 0; // Общее количество данных без учета постраничного просмотра

        $scope._onDblClick = this._onDblClick;
    }

    _onDblClick(row) {
        console.log('dbl click');
        if (row.entity) {
            console.log('id = ' + row.entity.id);
        }
    }

    /**
     * Вызывается при инициализации таблицы
     */
    onRegisterApi(gridApi) {
        this.grid = gridApi.grid;
        gridApi.core.on.sortChanged(this.$scope, this._onSortChanged);
        gridApi.selection.on.rowSelectionChanged(this.$scope, this._onRowSelectionChanged);
        gridApi.selection.on.rowSelectionChangedBatch(this.$scope, this._onRowSelectionChangedBatch);
        gridApi.pagination.on.paginationChanged(this.$scope, this._onPaginationChanged);
    }

    /**
     * Обработчик смены условий сортировки данных
     */
    _onSortChanged(grid, sortColumns) {
        console.log('sortChanged');
        this.grid.options.sort = sortColumns;
        this.grid.options.fetchData();
    };

    /**
     * Вызывается при выделении(снятии выделения) строки
     */
    _onRowSelectionChanged(row) {
        console.log('rowSelectionChanged');
        //row.isSelected;
        //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow);
        //todo
    };

    /**
     * Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
     */
    _onRowSelectionChangedBatch(rows) {
        console.log('rowSelectionChangedBatch');
        //rows.length
        //$scope.numberOfSelectedItems = rows.length;
        //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow)
        //todo
    };

    /**
     * Вызывается при смене номера текущей страницы, либо изменения количества отображаемых записей страницы
     */
    _onPaginationChanged(pageNumber, pageSize) {
        console.log('paginationChanged');
        this.grid.options.fetchData();
    };

    /**
     * Получение данных для выбранной страницы таблицы. Вызывается каждый раз, когда меняются условия
     * отображения данных: смена сортировки, переход на другую страницу, смена условий фильтрации данных.
     */
    async fetchData() {
        console.log('fetchData');
        // Формирование параметров запроса
        let params = this._getRequestParams();
        // Получение данных. Одновременная отправка двух запросов
        let result = await Promise.all([
            this.entityClass.count(params),
            this.entityClass.list(params)

        ]);
        // Общее количество
        this.totalItems = result[0];
        // Данные строк таблицы
        let rows = [];
        let limit = this.paginationPageSize;
        result[1].every(obj => {
            rows.push(this.entityClass.parse(obj));
            return rows.length < limit;
        });
        this.data = rows;
        //todo
        //updateViewData();
    }

    /**
     * Дополнительные параметры фильтрации данных. Переопределяется в классах-наследниках
     * @return
     * {
     *      name1: value1,
     *      name2: value2,
     *      ...
     *      nameN: valueN
     * }
     */
    _getFilter() {
        console.log('get filter');
        return null;
    }

    /**
     * Формирование параметров запроса: пейджинг, сортировка + пользовательские фильтры
     */
    _getRequestParams() {
        let params = {};
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
        // Дополнительные условия фильтрации
        let filter = this._getFilter();
        if (filter) {
            angular.extend(params, filter);
        }
        return params;
    };

}

export {AbstractGrid};