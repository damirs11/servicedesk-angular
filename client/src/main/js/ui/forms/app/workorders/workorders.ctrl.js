import {WorkordersGridOptions as GridOptions} from "./grid.config";

class WorkordersController {
    static $inject = ['SD', "$scope"];

    constructor(SD, $scope) {
        this.gridOptions = new GridOptions($scope);
    }

}

export {WorkordersController};
