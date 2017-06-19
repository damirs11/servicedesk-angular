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
                    //enableFullRowSelection: true,
                    enableSelectAll: true,
                    selectionRowHeaderWidth: 35,
                    multiSelect: true,
                    rowHeight: 30,
                    showGridFooter: true,
                    enableFiltering: true,
                    paginationPageSizes: [10, 20, 50, 100, 200, 500],
                    paginationPageSize: $scope.dataOptions.paging.pageSize,
                    columnDefs: $scope.dataOptions.metaData,
                    data: [
                        {id: 1, name: 'Вася', age: 15},
                        {id: 2, name: 'Петя', age: 25},
                        {id: 3, name: 'Андрей', age: 43},
                        {id: 4, name: 'Коля', age: 62},
                        {id: 5, name: 'Петр', age: 8}
                    ],
                    rowTemplate: "<div ng-dblclick=\"grid.appScope.onDblClick(row)\" ng-repeat=\"(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name\" class=\"ui-grid-cell\" ng-class=\"{ 'ui-grid-row-header-cell': col.isRowHeader }\" ui-grid-cell ></div>",

                    isRowSelectable: function (row) {
                        // условие
                        //row.entity.age > 30
                    },
                    appScopeProvider: {
                        onDblClick: function (row) {
                            if (row.entity) {
                                alert('dbl click ' + row.entity.name);
                            }
                        }
                    },
                    onRegisterApi: function (gridApi) {
                        //set gridApi on scope
                        $scope.gridApi = gridApi;
                        gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                            $scope.dataOptions.sort = sortColumns;
                            fetchData()
                        });
                        gridApi.selection.on.rowSelectionChanged($scope, function (row) {
                            //row.isSelected;
                            // Вызывается при выделении(снятии выделение) строки
                            //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow);
                        });
                        gridApi.selection.on.rowSelectionChangedBatch($scope, function(rows){
                            //rows.length
                            // Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
                            $scope.numberOfSelectedItems = rows.length;
                            //setCurrentRow($scope.gridApi.grid.selection.lastSelectedRow)
                        });
                        gridApi.pagination.on.paginationChanged($scope, function (pageNumber, pageSize) {
                            if ($scope.dataOptions.paging.pageSize != pageSize) {
                                // В случае изменения значения параметра "Кол-во записей на странице" переходим на 1-ю страницу
                                $scope.dataOptions.paging.pageNumber = 1;
                                $scope.gridOptions.paginationCurrentPage = 1
                            } else {
                                $scope.dataOptions.paging.pageNumber = pageNumber
                            }
                            $scope.dataOptions.paging.pageSize = pageSize;
                            fetchData()
                        });
                    }
                };
                var fetchData = function () {
                    console.info('fetch data')
                }
            }]);

}());