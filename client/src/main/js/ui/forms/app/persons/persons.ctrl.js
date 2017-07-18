import {PersonsGridOptions as GridOptions} from "./grid.config";

class PersonsController {
    static $inject = ['SD', "$scope"];

    constructor(SD, $scope) {
        this.gridOptions = new GridOptions($scope);
    }

}

export {PersonsController};
