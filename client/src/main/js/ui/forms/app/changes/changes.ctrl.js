import {ChangesGridOptions} from './grid.config';

class ChangesController {
    static $inject = ['SD', '$scope', '$connector'];

    constructor(SD, $scope, $connector) {
        this.SD = SD;
        this.$scope = $scope;
        this.$connector = $connector;
    }

    $onInit () {
        this.gridOptions = new ChangesGridOptions(this.$scope, this.$connector, this.SD.Change);
        this.gridOptions.fetchData();
    }
}

export {ChangesController};