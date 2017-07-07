import rowTemplate from "./row.html";

/**
 * Абстрактный класс для работы с таблицой. Содержит базовые настройки и обработчики событий.
 */
class AbstractGrid {

    constructor($scope) {
        if (this.constructor === AbstractGrid) {
            throw new TypeError('Нельзя создавать экземпляры абстрактного класса "AbstractGrid"!');
        }
        this.$scope = $scope;
        this.grid = null; // Выставляем при инициализации таблицы
        this.sort = null; // Параметры сортировки данных

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
        this.paginationPageSizes = [10, 20, 50, 100, 200, 500, 1000];
        this.paginationPageSize = 20;
        this.paginationCurrentPage = 1;
        this.useExternalPagination = true;
        this.useExternalSorting = true;
        this.rowTemplate = rowTemplate; // Необходим для расширения функционала работы со строкой таблицы
        this.columnDefs = []; // Описание колонок таблицы
        this.data = []; // Содержимое таблицы
        this.totalItems = 0; // Общее количество данных без учета постраничного просмотра
    }

    /**
     * Расширение событийной модели строки таблицы. Реализация реакции на двойной клик.
     */
    gridScopeProvider() {
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
        gridApi.core.on.sortChanged(this.$scope, this.onSortChanged);
        gridApi.selection.on.rowSelectionChanged(this.$scope, this.onRowSelectionChanged);
        gridApi.selection.on.rowSelectionChangedBatch(this.$scope, this.onRowSelectionChangedBatch);
        gridApi.pagination.on.paginationChanged(this.$scope, this.onPaginationChanged);
    }

    /**
     * Обработчик смены условий сортировки данных
     */
    onSortChanged(grid, sortColumns) {
        console.log('sortChanged');
        this.grid.options.sort = sortColumns;
        this.grid.options.fetchData();
    };

    /**
     * Вызывается при выделении(снятии выделения) строки
     */
    onRowSelectionChanged(row) {
        console.log('rowSelectionChanged');
        //row.isSelected;
        //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow);
    };

    /**
     * Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
     */
    onRowSelectionChangedBatch(rows) {
        console.log('rowSelectionChangedBatch');
        //rows.length
        //$scope.numberOfSelectedItems = rows.length;
        //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow)
    };

    /**
     * Вызывается при смене номера текущей страницы, либо изменения количества отображаемых записей страницы
     */
    onPaginationChanged(pageNumber, pageSize) {
        console.log('paginationChanged');
        this.grid.options.fetchData();
    };

    /**
     * Получение данных для выбранной страницы таблицы. Вызывается каждый раз, когда меняются условия
     * отображения данных: смена сортировки, переход на другую страницу, смена условий фильтрации данных.
     */
    fetchData() {
        console.log('fetchData');
    }

}

export {AbstractGrid};