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
    }

    init() {
        this.fetchData();
    }

    /**
     * Расширение событийной модели строки таблицы. Реализация реакции на двойной клик.
     */
    appScopeProvider() {
        //todo не работает !!!
        onDblClick = function (row) {
            console.log('dbl click');
            if (row.entity) {
                console.log('id = ' + row.entity.id);
            }
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
        // Получение данных
        let data = this.entityClass.list(params);
        console.log('data: ' + data);
        if (data) {
            this.totalItems = data.total;
            let rows = [];
            let clazz = this.entityClass;
            $.each(data.list, function(i, obj) {
                rows.push(clazz.parse(obj));
            });
            // Проверка, что получено данных не более чем запросили. Такое может случиться,
            // если ДАО содержит ошибки
            if (rows.length > this.$scope.dataOptions.paging.pageSize) {
                // Обрезаем до требуемого количества
                rows = rows.slice(0, this.$scope.dataOptions.paging.pageSize)
            }

            //todo
            //updateViewData();

            console.log('rows: ' + rows);
        }
    }

    /**
     * Дополнительные параметры фильтрации данных. Переопределяется в классах-наследниках
     */
    _getFilter() {
        return null;
    }

    /**
     * Формирование параметров запроса: пейджинг, сортировка + пользовательские фильтры
     */
    _getRequestParams() {
        let params = {};
        // Параметры пейджинга
        let from = this.grid.options.paginationPageSize * (this.grid.options.paginationCurrentPage - 1) + 1;
        let to = from + this.grid.options.paginationPageSize - 1;
        params.paging = from + ';' + to;
        // Параметры сортировки
        let sortColumns = this.grid.options.sort;
        if (sortColumns && sortColumns.length > 0) {
            let sort = '';
            sortColumns.forEach(function (column) {
                sort += column.field + '-' + column.sort.direction + ';'
            });
            params.sort = sort
        }
        let filter = this._getFilter();
        if (filter) {
            angular.extend(params, filter);
        }
        return params
    };

}

export {AbstractGrid};