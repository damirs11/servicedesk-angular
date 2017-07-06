(function () {
    'use strict';

    angular
        .module('ui.rest.grid', [
            'ui.grid',
            'ui.grid.selection',
            'ui.grid.pagination'
        ])
        .directive('restGrid', function () {
            return {
                templateUrl: 'js/app/widget/grid/rest-grid.html',
                controller: 'restGridController'
            }
        })
        .controller('restGridController', [
            '$scope',
            function ($scope) {
                $scope.dataOptions = {
                    paging: {pageNumber: 1, pageSize: 20},
                    sort: null,
                    filter: {}, // Параметры фильтрации данных
                    metaData:  // Информация о классе отображаемых данных
                        [
                            {name: 'id'},
                            {name: 'name'},
                            {name: 'age', displayName: 'Age (not focusable)', allowCellFocus: false}
                        ]
                };

                $scope.gridOptions = {
                    enableSorting: true,
                    excludeSortColumns: true,
                    enableRowSelection: true,
                    enableSelectAll: true,
                    selectionRowHeaderWidth: 35,
                    multiSelect: true,
                    rowHeight: 30,
                    showGridFooter: true,
                    enableFiltering: true,
                    paginationPageSizes: [10, 20, 50, 100, 200, 500],
                    paginationPageSize: $scope.dataOptions.paging.pageSize,
                    useExternalPagination : true,
                    columnDefs: $scope.dataOptions.metaData,
                    data: [],
                    totalItems: 0,
                    rowTemplate: "<div ng-dblclick=\"grid.appScope.onDblClick(row)\" ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell ></div>",

                    isRowSelectable: function (row) {
                        // условие
                        //row.entity.age > 30
                    },
                    appScopeProvider: {
                        onDblClick: function (row) {
                            if (row.entity) {
                                alert('dbl click ' + row.entity.id);
                            }
                        }
                    },
                    onRegisterApi: function (gridApi) {
                        //set gridApi on scope
                        $scope.gridApi = gridApi;
                        gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                            console.log('sortChanged');
                            $scope.dataOptions.sort = sortColumns;
                            fetchData();
                        });
                        gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                            console.log('rowSelectionChanged');
                            //row.isSelected;
                            // Вызывается при выделении(снятии выделение) строки
                            //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow);
                        });
                        gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows){
                            console.log('rowSelectionChangedBatch');
                            //rows.length
                            // Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
                            $scope.numberOfSelectedItems = rows.length;
                            //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow)
                        });
                        gridApi.pagination.on.paginationChanged($scope, function (pageNumber, pageSize) {
                            console.log('paginationChanged');
                            if ($scope.dataOptions.paging.pageSize != pageSize) {
                                // В случае изменения значения параметра "Кол-во записей на странице" переходим на 1-ю страницу
                                $scope.dataOptions.paging.pageNumber = 1;
                                $scope.gridOptions.paginationCurrentPage = 1;
                                $scope.dataOptions.paging.pageSize = pageSize;
                            } else {
                                $scope.dataOptions.paging.pageNumber = pageNumber
                            }
                            fetchData();
                        });
                    }
                };
                var fetchData = function () {
                    var page = $scope.dataOptions.paging.pageNumber;
                    var size = $scope.dataOptions.paging.pageSize;
                    var rows = [];
                    console.info('fetch data');
                    for (var i = (page - 1) * size; i < page * size; i++) {
                        rows.push({
                            id: i,
                            name: "Вася",
                            age: Math.round((Math.random() * 40 + 10))
                        })
                    }
                    $scope.gridOptions.data = rows;
                    $scope.gridOptions.totalItems = 300;
                };
                fetchData();
            }]);

}());