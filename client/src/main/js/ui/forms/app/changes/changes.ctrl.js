import {ChangesGridOptions} from './grid.config';

class ChangesController {
    static $inject = ['SD', '$scope', '$connector'];

    constructor(SD, $scope, $connector) {
        this.gridOptions = new ChangesGridOptions($scope, $connector, SD.Change);
    }

}

export {ChangesController};

/*[
 { field: 'status', name: "Статус" },
 { field: 'description', name: "Тема" },
 { field: 'name', name: "NAME" },
 { field: 'date', name: "DATE", enableColumnResizing: false, cellFilter: "dd.MM.yyyy"},
 { field: 'prefix', name: "PREFIX", cellTooltip: true },
 { field: 'company', name: "COMPANY", enableColumnMenu: false }
 ]

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
 */