import rowTemplate from "./row.html";

/**
 * Абстрактный класс для работы с таблицой. Содержит базовые настройки и обработчики событий.
 */
class AbstractGrid {

    constructor($scope, $connector, entityClass, $parse, $timeout) {
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
        this.$parse = $parse;
        this.$timeout = $timeout;
        this.entityClass = entityClass; // Класс, данные которого будут отображаться в таблице
        this.grid = null; // Выставляем при инициализации таблицы
        this.sort = []; // Параметры сортировки данных

        // Свойства таблицы ui-grid
        this.enableCellEdit = false; // Запрещаем редактирование
        this.enableSorting = true; // Включаем сортировку
        this.excludeSortColumns = true;

        this.enableHorizontalScrollbar = 2; // скороллбар в бок
        this.enableVerticalScrollbar = 2; // ставим скроллбар наверз "WHEN_NEEDED"

        // Настройки сохранения состояния таблицы
        this.saveWidths = true;
        this.saveOrder = true;
        this.saveScroll = true;
        this.saveFocus = false;
        this.saveVisible = true;
        this.saveSort = true;
        this.saveFilter = true;
        this.savePinning = true;
        this.saveGrouping = true;
        this.saveGroupingExpandedStates = true;
        this.saveTreeView = false;
        this.saveSelection = false;

        this.enableRowSelection = true; // Дополнительный столбец для выбора строк таблицы
        this.enableSelectAll = true; // Галочка в заголовке дополнительного столбца
        this.selectionRowHeaderWidth = 30;
        this.enableFullRowSelection = true; // Включает выделение строки по клику на ней
        this.rowHeight = 20;
        this.enableFiltering = false; //Включает поле для быстрой фильтрации данных на странице
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

        this.enableGridMenu = true;
        this.exporterCsvFilename = 'myFile.csv';
        this.exporterPdfDefaultStyle = {fontSize: 9};
        this.exporterPdfTableStyle = {margin: [30, 30, 30, 30]};
        this.exporterPdfTableHeaderStyle = {fontSize: 10, bold: true, italics: true, color: 'black'};
        this.exporterPdfHeader = { text: "Экспорт данных в PDF", style: 'headerStyle' };
        this.exporterPdfFooter = function ( currentPage, pageCount ) {
            return { text: currentPage.toString() + ' из ' + pageCount.toString(), style: 'footerStyle' };
        };
        this.exporterPdfCustomFormatter = function ( docDefinition ) {
            docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
            docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
            return docDefinition;
        };
        this.exporterPdfOrientation = 'landscape';
        this.exporterPdfPageSize = 'LETTER';
        this.exporterOlderExcelCompatibility = true; // Правка кодировки
        this.exporterCsvColumnSeparator = ';'; // Разделение в csv
        this.exporterPdfMaxGridWidth = 500;
        this.exporterCsvLinkElement = angular.element(document.querySelectorAll(".custom-csv-link-location"));

        this.exporterFieldCallback = ::this._export;
        $scope._onDblClick = this._onDblClick;
    }

    getSelectedRows(){
        return this.gridApi.selection.getSelectedRows();
    }

    gridName = "ui-grid";
    enableSaving = false;
    _saveOptions(){
        console.log("SAVE, "+this.gridName);
        const snapshot = this.gridApi.saveState.save();
        localStorage[this.gridName] = JSON.stringify(snapshot)
    }

    _loadOptions(){
        console.log("Loading");
        const settings = JSON.parse(localStorage[this.gridName]);
        if (!settings) return;
        this.gridApi.saveState.restore(this.$scope,settings);
    }

    /**
     * Функция для экспорта данных из таблицы
     * Применяет ангуляровские фильтры на значения
     */
    _export(grid, row, col, input) {
        if (!input) return "";
        if (input.constructor && input.constructor.name == "Person") { // Парсинг персон
            input = input.shortName;
        }
        if (col.cellFilter) { // Применение фильтров
            const parse = this.$parse("$parseGridColValue |"+col.cellFilter);
            const parsed = parse && parse({$parseGridColValue:input});
            if (parsed == null) return "";
            return String(parsed);
        } else {
            return String(input);
        }
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
        this.gridApi = gridApi;
        this.grid = gridApi.grid;
        gridApi.core.on.sortChanged(this.$scope, this._onSortChanged);
        gridApi.selection.on.rowSelectionChanged(this.$scope, this._onRowSelectionChanged);
        gridApi.selection.on.rowSelectionChangedBatch(this.$scope, this._onRowSelectionChangedBatch);
        gridApi.pagination.on.paginationChanged(this.$scope, this._onPaginationChanged);
        if (this.enableSaving) {
            gridApi.core.on.renderingComplete(this.$scope, () => {
                this.$timeout(() => {
                    this._loadOptions();
                    gridApi.core.on.sortChanged(this.$scope, ::this._saveOptions);
                    gridApi.core.on.filterChanged(this.$scope, ::this._saveOptions);
                    gridApi.core.on.columnVisibilityChanged(this.$scope, ::this._saveOptions);
                },0)
            })
        }
    }

    /**
     * Обработчик смены условий сортировки данных
     */
    _onSortChanged(grid, sortColumns) {
        console.log('sortChanged');
        this.sort = sortColumns;
        this.fetchData();
    };

    /**
     * Вызывается при выделении(снятии выделения) строки
     */
    _onRowSelectionChanged(row) {
        console.log('rowSelectionChanged');
        //$scope.gridApi.grid.selection.lastSelectedRow
    };

    /**
     * Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
     */
    _onRowSelectionChangedBatch(rows) {
        console.log('rowSelectionChangedBatch');
        //rows.length
        //$scope.numberOfSelectedItems = rows.length
        //$scope.gridApi.grid.selection.lastSelectedRow
    };

    /**
     * Вызывается при смене номера текущей страницы, либо изменения количества отображаемых записей страницы
     */
    _onPaginationChanged(pageNumber, pageSize) {
        console.log('paginationChanged');
        this.fetchData();
    };

    /**
     * Получение данных для выбранной страницы таблицы. Вызывается каждый раз, когда меняются условия
     * отображения данных: смена сортировки, переход на другую страницу, смена условий фильтрации данных.
     */
    async fetchData(searchParams) {
        console.log('fetchData');
        // Формирование параметров запроса
        let params = this._getRequestParams(searchParams);
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
    _getRequestParams(searchParams) {
        let params = searchParams || {};
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