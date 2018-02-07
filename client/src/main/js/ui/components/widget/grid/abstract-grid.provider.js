/**
 * Абстрактный класс для работы с таблицой. Содержит базовые настройки и обработчики событий.
 */
import rowTemplate from "./row.html";
import {Enumerable} from "../../../../common/decorator/enumerable.decorator";

AbstractGridProvider.$inject = ["$parse", "$timeout"];
function AbstractGridProvider($parse, $timeout) {
    /**
     * Таблица изменений
     * @class
     * @name $grid.AbstractGrid
     */
    return class AbstractGrid {

        constructor($scope, entityClass) {
            if (!$scope) {
                throw new TypeError('Аргумент "$scope" должен быть задан!');
            }
            if (!entityClass) {
                throw new TypeError('Аргумент "entityClass" должен быть задан!');
            }
            this.$scope = $scope;
            this.entityClass = entityClass; // Класс, данные которого будут отображаться в таблице
            this.grid = null; // Выставляем при инициализации таблицы
            // this.sort = []; // Параметры сортировки данных

            // Свойства таблицы ui-grid
            this.enableCellEdit = false; // Запрещаем редактирование
            this.enableSorting = true; // Включаем сортировку
            this.excludeSortColumns = true;

            this.enableHorizontalScrollbar = 2; // скороллбар в бок
            this.enableVerticalScrollbar = 2; // ставим скроллбар наверз "WHEN_NEEDED"

            this.saveWidths = true;
            this.saveOrder = true;
            this.saveScroll = true;
            this.saveFocus = false;
            this.saveVisible = true;
            this.saveSort = false;
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

            $scope._onDblClick = ::this._onDblClick;

            this.searchParamsContainer = new GridPropertyContainer();
            // При любом изменении кидаем запрос данных на сервер
            this.searchParamsContainer.on("change", () => {
                this.fetchData();
            });
        }

        /**
         * Критерии поиска таблицы.
         * При их обновлении таблица запрашивает данные с сервера.
         * @property $grid.AbstractGrid#searchParamsContainer
         * @type {GridPropertyContainer}
         */
        searchParamsContainer;

        initializeSearchParams(params) {
            // Заносим туда переданные данные, но без кидания эвента об их изменении
            if (params) this.searchParamsContainer.quietAdd(params);
            // Если есть сортировка, выставляем в таблице сортировку
            if (params && params.sort) {
                const sField = params.sort.split("-")[0];
                const sDir = params.sort.split("-")[1];
                console.log("Sorting table by",sField,sDir);
                this.sortBy([{field:sField,direction:sDir}])
            }
            this.fetchData();
        }

        getSelectedRows(){
            return this.gridApi.selection.getSelectedRows();
        }

        gridName = "ui-grid";
        enableSaving = false;
        /**
         * Сохраняет данные таблицы.
         * @private
         */
        _saveOptions(){
            const snapshot = this.gridApi.saveState.save();
            localStorage[this.gridName] = JSON.stringify(snapshot)
        }
        /**
         * Загружает состояние таблицы.
         * Сюда входят параметры в стиле: какие столбцы скрыты, размеры и т.п.
         * @private
         */
        _loadOptions(){
            const saveData = localStorage[this.gridName];
            if (!saveData) return;
            const settings = JSON.parse(saveData);
            // this.gridApi.saveState.restore(this.$scope,settings);
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
                const parse = $parse("$parseGridColValue |"+col.cellFilter);
                const parsed = parse && parse({$parseGridColValue:input});
                if (parsed == null) return "";
                return String(parsed);
            } else {
                return String(input);
            }
        }


        _onDblClick(row) {
            this._broadcastEvent("grid:double-click",{row});
        }

        /**
         * Задать сортировку
         * @param columns {Object[]}
         * @param columns[].field {string} - название столбца
         * @param columns[].direction {string} - asc/desc направление сортировки
         * @param [add] {boolean} - добавить к прошлой сортировке
         */
        sortBy(columns,add){
            // Преобразуем в вид {стобец_из_таблицы,переданный_столбец}
            const sortInfos = columns.map(col => {
                const gridColumn = this.grid.columns.find(gridColumn => gridColumn.field == col.field);
                return {gridColumn, col}
            });
            for (let i = 0; i < sortInfos.length; i++) {
                const info = sortInfos[i];
                const addToPrevSorting = i==0 ? undefined : add;
                this.grid.sortColumn(info.gridColumn,info.col.direction, addToPrevSorting)
            }
        }

        /**
         * Перейти на страницу
         * @param page {number} - номер страницы
         */
        changePage(page){
            this.gridApi.pagination.seek(page);
        }

        /**
         * Вызывается при инициализации таблицы
         */
        onRegisterApi(gridApi) {
            this.gridApi = gridApi;
            this.grid = gridApi.grid;

            const configureGridAPI = () => {
                gridApi.core.on.sortChanged(this.$scope, ::this._onSortChanged);
                gridApi.selection.on.rowSelectionChanged(this.$scope, ::this._onRowSelectionChanged);
                gridApi.selection.on.rowSelectionChangedBatch(this.$scope, ::this._onRowSelectionChangedBatch);
                gridApi.pagination.on.paginationChanged(this.$scope, ::this._onPaginationChanged);
            };

            if (!this.enableSaving) {
                configureGridAPI()
            } else {
                gridApi.core.on.renderingComplete(this.$scope, () => {
                    $timeout(() => {
                        this._loadOptions();
                        gridApi.core.on.sortChanged(this.$scope, ::this._saveOptions);
                        gridApi.core.on.filterChanged(this.$scope, ::this._saveOptions);
                        gridApi.core.on.columnVisibilityChanged(this.$scope, ::this._saveOptions);
                        configureGridAPI()
                    },0)
                })
            }
        }

        /**
         * Обработчик смены условий сортировки данных
         */
        _onSortChanged(grid, sortColumns) {
            this._broadcastEvent("grid:sort-changed",{sortColumns});
            let sortField = undefined;
            if (sortColumns[0] != undefined) {
                sortField = `${sortColumns[0].field}-${sortColumns[0].sort.direction}`;
            }
            this.searchParamsContainer.add({sort: sortField})
        };
        /**
         * Вызывается при выделении(снятии выделения) строки
         */
        _onRowSelectionChanged(row) {
            this._broadcastEvent("grid:selection-changed",{row});
        };
        /**
         * Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
         */
        _onRowSelectionChangedBatch(rows) {
            this._broadcastEvent("grid:selection-changed=batch",{rows});
        };

        /**
         * Вызывается при смене номера текущей страницы, либо изменения количества отображаемых записей страницы
         */
        _onPaginationChanged(page, pageSize) {
            this._broadcastEvent("grid:pagination-changed",{page,pageSize});
            const from = this.paginationPageSize * (this.paginationCurrentPage - 1) + 1;
            const to = from + this.paginationPageSize - 1;
            this.searchParamsContainer.add({paging: `${from};${to}`});
        };

        /**
         * Возвращает объект, содержащий все поля, по которым будет производиться поиск даннных
         */
        getFullSearchParams(){
            const fullParams = Object.create(null);
            let keys = Object.keys(this.searchParamsContainer);
            for (let i in keys) {
                const key = keys[i];
                fullParams[key] = this.searchParamsContainer[key]
            }
            // Т.к. со страницами пока проблема, каждый раз заносим нужные страницы в параметры
            const from = this.paginationPageSize * (this.paginationCurrentPage - 1) + 1;
            const to = from + this.paginationPageSize - 1;
            fullParams["paging"] = `${from};${to}`;
            return fullParams;
        }

        // Текущий запрос данных
        currentFetchPromise = null;
        /**
         * Получение данных для выбранной страницы таблицы. Вызывается каждый раз, когда меняются условия
         * отображения данных: смена сортировки, переход на другую страницу, смена условий фильтрации данных.
         */
        async fetchData() {
            // Формирование параметров запроса
            const params = this.getFullSearchParams();
            if ('GULP_REPLACE:DEBUG') console.log("Grid-fetch",params);
            // Получение данных. Одновременная отправка двух запросов
            const fetchPromise = this.currentFetchPromise = Promise.all([
                this.entityClass.count(params),
                this.entityClass.list(params)
            ]);
            this._broadcastEvent("grid:fetch",{params,fetchPromise});
            const result = await fetchPromise;
            if (this.currentFetchPromise != fetchPromise) return; // Если уже начался другой поиск.
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
         * Пушит событие
         * @private
         */
        _broadcastEvent(name,data){
            data.grid = this;
            this.$scope.$broadcast(name,data)
        }
    }
}

class GridPropertyContainer {

    @Enumerable(false)
    eventHandlers = {};

    /**
     * Задает поля в объект и кидает эвенты об обновлении.
     * Для того, чтобы задать поле и не кидать эвент
     * достаточно сделать obj["fieldName"] = value;
     * или quietAdd(obj)
     * Т.к. все эти значения заносятся прямо в этот объект.
     * @param properties
     */
    add(properties) {
        const keys = Object.keys(properties);
        for (let key of keys) {
            if (properties[key] === undefined) {
                delete this[key];
            } else {
                this[key] = properties[key]
            }
            this._notifyPropertyChange(key,this[key],properties[key]);
        }
        this._notifyPropertyChange()
    }

    /**
     * Задает поля в объект, но не кидает эвент
     * @param properties
     */
    quietAdd(properties) {
        const keys = Object.keys(properties);
        for (let key of keys) {
            if (properties[key] === undefined) {
                delete this[key];
            } else {
                this[key] = properties[key]
            }
        }
    }

    _notifyPropertyChange(propertyName,oldValue,newValue) {
        let eventName = propertyName != null ? `change:${propertyName}` : "change";
        const handlers = this.eventHandlers[eventName];
        if (handlers == null) return;
        handlers.forEach(h => h(this,oldValue,newValue))
    }

    /**
     * Подписаться на эвент.
     * Эвенты:
     * change - общий эвент обновления. Кидается последним
     * change:%fieldName% - эвент обновления кокретного поля.
     * где fieldName - название поля.
     * Часто таблицы завязаны на эти эвенты и делают fetch при эвенте change.
     * (Если, конечно, класс используется как параметры поиска)
     */
    on(eventName, handler) {
        let handlerList = this.eventHandlers[eventName];
        if (handlerList == null) {
            handlerList = this.eventHandlers[eventName] = [];
        }
        handlerList.push(handler)
    }

    /**
     * Отписаться от эвента
     */
    off(eventName, handler) {
        const handlers = this.eventHandlers[eventName];
        if (handlers == null) return;
        const index = handlers.indexOf(handler);
        if (index < 0) return;
        handlers.splice(index,1)
    }
}

export {AbstractGridProvider, GridPropertyContainer};