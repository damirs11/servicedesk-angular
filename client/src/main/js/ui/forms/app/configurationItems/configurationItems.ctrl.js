import {ConfigurationItemsGridOptions as GridOptions} from "./grid.config";

class ConfigurationItemsController {
    static $inject = ['SD', "$scope"];

    constructor(SD, $scope) {
        this.gridOptions = new GridOptions($scope);
    }

}

export {ConfigurationItemsController};
