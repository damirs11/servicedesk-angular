import {QuestionsGridOptions as GridOptions} from "./grid.config";

class QuestionsController {
    static $inject = ['SD', "$scope"];

    constructor(SD, $scope) {
        this.gridOptions = new GridOptions($scope);
    }

}

export {QuestionsController};
