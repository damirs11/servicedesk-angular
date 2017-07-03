/**
 * Изменения. Журнальная форма
 */
(function () {
    'use strict';

    angular.module('appChanges', [
            'appUserData',
            'ngMessages',
            'ui.router',
            'ui.grid'
        ])
        .config(function ($stateProvider) {
        $stateProvider
            .state('changes', {
                url: '/changes',
                templateUrl: 'js/app/changes/changes.html',
                controller: 'appChangesController'
            })
        })
        .controller('appChangesController', [
            '$scope', '$translate', 'USER_DATA', '$http', '$state',
            function ($scope, $translate, USER_DATA, $http, $state) {
                // Параметры отображения данных таблицы
                $scope.dataOptions = {
                    paging: {pageNumber: 1, pageSize: 20},
                    sort: null,
                    filter: {}, // Параметры фильтрации данных
                    metaData:  // Информация о классе отображаемых данных
                        [
                            { field: 'status', name: "Статус" },
                            { field: 'description', name: "Тема" },
                            { field: 'name', name: "NAME" },
                            { field: 'date', name: "DATE", enableColumnResizing: false, cellFilter: "dd.MM.yyyy"},
                            { field: 'prefix', name: "PREFIX", cellTooltip: true },
                            { field: 'company', name: "COMPANY", enableColumnMenu: false }
                        ]
                };
                $scope.gridOptions = {
                    paginationPageSizes: [10, 20, 50, 100, 200, 500],
                    paginationPageSize: $scope.dataOptions.paging.pageSize,
                    rowSelection: true,
                    useExternalPagination: true,
                    useExternalSorting: true,
                    enableFullRowSelection: true,
                    multiSelect: true,
                    modifierKeysToMultiSelect: true,
                    columnDefs: $scope.dataOptions.metaData,
                    appScopeProvider: {
                        onDblClick: function (row) {
                            if (row.entity) {
                                $state.go("state куда переходим", {
                                    remainId: row.entity.id
                                    // прочие параметры для пересылки
                                    //remainsReport: aplanaEntityUtils.getDataByView($scope.dataOptions.data, row.entity)
                                })
                            }
                        }
                    },
                    rowTemplate: "<div ng-dblclick=\"grid.appScope.onDblClick(row)\" ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell ></div>",
                    onRegisterApi: function (gridApi) {
                        $scope.gridApi = gridApi;
                        $scope.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                            $scope.dataOptions.sort = sortColumns;
                            fetchData()
                        });
                        $scope.gridApi.pagination.on.paginationChanged($scope, function (pageNumber, pageSize) {
                            // В случае изменения значения параметра "Кол-во записей на странице" переходим на 1-ю страницу
                            if ($scope.dataOptions.paging.pageSize != pageSize) {
                                $scope.dataOptions.paging.pageNumber = 1;
                                $scope.gridOptions.paginationCurrentPage = 1
                            } else {
                                $scope.dataOptions.paging.pageNumber = pageNumber
                            }
                            $scope.dataOptions.paging.pageSize = pageSize;
                            fetchData()
                        });
                        $scope.gridApi.selection.on.rowSelectionChanged($scope, function (row, evt) {
                            // Вызывается при выделении(снятии выделение) строки
                            setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow);
                        });
                        $scope.gridApi.selection.on.rowSelectionChangedBatch($scope, function (rows, evt) {
                            // Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
                            $scope.numberOfSelectedItems = $scope.gridApi.grid.selection.selectedCount;
                            setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow)
                        });
                    }
                };
                // Формирование параметров запроса
                var getRequestParams = function () {
                    var params = {};
                    // Параметры пейджинга
                    var from = $scope.dataOptions.paging.pageSize * ($scope.dataOptions.paging.pageNumber - 1) + 1;
                    var to = from + $scope.dataOptions.paging.pageSize - 1;
                    params.paging = from + ';' + to;
                    // Параметры сортировки
                    if ($scope.dataOptions.sort && $scope.dataOptions.sort.length != 0) {
                        var sort = '';
                        $scope.dataOptions.sort.forEach(function (column) {
                            sort += column.field + '-' + column.sort.direction + ';'
                        });
                        params.sort = sort
                    }
                    angular.extend(params, $scope.dataOptions.filter);
                    return params
                };
                /**
                 * Обновляет данные в таблице с учетом фильтрации, выполняет запрос к серверу
                 */
                var fetchData = function () {
                    // Формирование параметров запроса
                    var params = getRequestParams();
                    // Получение данных
                    $http.get('rest/entity/Change', {params: params})
                        .then(function (response) {
                            var rows = response.data.list;
                            if (rows) {
                                // Проверка, что получено данных не более чем запросили. Такое может случиться,
                                // если ДАО содержит ошибки
                                if (rows.length > $scope.dataOptions.paging.pageSize) {
                                    // Обрезаем до требуемого количества
                                    rows = rows.slice(0, $scope.dataOptions.paging.pageSize)
                                }
                                $scope.dataOptions.data = rows;
                                $scope.gridOptions.totalItems = response.data.total;
                                updateViewData();
                            }
                        })
                        // .then(function () {
                        //     //Привязываем параметры запроса к контексту функций, возвращающих отчёты
                        //     aplanaEntityUtils.setContext(params);
                        // })
                };

                /**
                 * Форматирует и обновляет выводимые на экран значения
                 */
                var updateViewData = function () {
                    var data = $scope.dataOptions.data;
                    var displayData = [];
                    // Изменяем выводимую информацию
                    data.forEach(function (row) {
                        displayData.push(getRowView(row))
                    });
                    $scope.gridOptions.data = displayData;
                    clearGridSelection()
                };

                var clearGridSelection = function () {
                    //$scope.gridApi.selection.clearSelectedRows()
                };

                /**
                 * Представление данных записи для отображения на экране
                 * @param row
                 * @returns {Array}
                 */
                var getRowView = function (row) {
                    var displayRow = [];
                    for (var field in row) {
                        var value = row[field]
                        switch (field) {
                            case "status": displayRow[field] = value == null ? null : value.name;
                                break
                            default: displayRow[field] = value
                        }
                    }
                    return displayRow
                };

                /**
                 * Следит за последней выделенной строкой.
                 * @param lastSelectedRow
                 */
                var setCurrentRow = function (lastSelectedRow) {
                    if (!lastSelectedRow.isSelected || $scope.gridApi.grid.selection.selectedCount != 1) {
                        $scope.currentRowView = {};
                        $scope.tempRowData = {};
                        $scope.tempRowView = {}
                    } else {
                        $scope.currentRowView = lastSelectedRow.entity;
                        var currenRowData = getDataByView($scope.dataOptions.data, $scope.currentRowView);
                        $scope.tempRowData = $scope.sendEntityData = angular.extend({}, currenRowData);
                        $scope.tempRowView = angular.extend({}, $scope.currentRowView);
                    }
                    $scope.numberOfSelectedItems = $scope.gridApi.grid.selection.selectedCount;
                };

                /**
                 * Функция для поиска в массиве строк, пришедших с сервера, строки, соответствующей той, что выделена в таблице
                 * @param data массив строк с сервера
                 * @param view выделенная строка
                 * @returns {*} строка, соответствующая выделенной.
                 */
                function getDataByView(data, view) {
                    return data.reduce(function (res, item) {
                        if (item.id == view.id) {
                            res = item;
                        }
                        return res; //есди совпало, возвращаем то, что совпало, иначе - то, что пришло.
                    }, {});
                }
                fetchData();
            }]);
}());