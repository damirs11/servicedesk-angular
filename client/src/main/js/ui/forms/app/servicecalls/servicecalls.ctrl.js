import {ServicecallsGridOptions as GridOptions} from "./grid.config";

class ServicecallsController {
    static $inject = ['SD', "$scope"];

    constructor(SD, $scope) {
        this.gridOptions = new GridOptions($scope);
    }

}

export {ServicecallsController};
