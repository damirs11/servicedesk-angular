import rowTemplate from "./row.tpl.html";

class ChangeListController {
    static $inject = ['SD',"$scope","ModalAction"];

    constructor(SD,$scope,ModalAction){


        this.dataOptions = {
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

        this.gridOptions = {
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
            rowTemplate: rowTemplate,
            onRegisterApi: this._onRegisterApi
        };
    }

    _onRegisterApi (gridApi) {
        this.gridApi = gridApi;
        gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
            $scope.dataOptions.sort = sortColumns;
            fetchData()
        });
        gridApi.pagination.on.paginationChanged($scope, function (pageNumber, pageSize) {
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
        gridApi.selection.on.rowSelectionChanged($scope, function (row, evt) {
            // Вызывается при выделении(снятии выделение) строки
            setCurrentRow(gridApi.grid.selection.lastSelectedRow);
        });
        gridApi.selection.on.rowSelectionChangedBatch($scope, function (rows, evt) {
            // Вызывается когда выделяются(снимается выделение) все строки щелчком по заголовку таблицы
            $scope.numberOfSelectedItems = gridApi.grid.selection.selectedCount;
            setCurrentRow(gridApi.grid.selection.lastSelectedRow)
        });
    }

    clearGridSelection () {
        //$scope.gridApi.selection.clearSelectedRows()
    }

    updateViewData () {
        var data = $scope.dataOptions.data;
        var displayData = [];
        // Изменяем выводимую информацию
        data.forEach(function (row) {
            displayData.push(getRowView(row))
        });
        $scope.gridOptions.data = displayData;
        clearGridSelection()
    }


    /**
     * Представление данных записи для отображения на экране
     * @param row
     * @returns {Array}
     */
    getRowView (row) {
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
    setCurrentRow (lastSelectedRow) {
        if (!lastSelectedRow.isSelected || this.gridApi.grid.selection.selectedCount != 1) {
            this.currentRowView = {};
            this.tempRowData = {};
            this.tempRowView = {}
        } else {
            this.currentRowView = lastSelectedRow.entity;
            var currenRowData = getDataByView(this.dataOptions.data, this.currentRowView);
            this.tempRowData = this.sendEntityData = angular.extend({}, currenRowData);
            this.tempRowView = angular.extend({}, this.currentRowView);
        }
        this.numberOfSelectedItems = this.gridApi.grid.selection.selectedCount;
    }
}

export {ChangeListController}