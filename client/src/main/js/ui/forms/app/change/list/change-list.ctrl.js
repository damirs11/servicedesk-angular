import {ChangesGridOptions} from './grid.config';

class ChangeListController {
    static $inject = ['SD', '$scope', '$connector', '$state'];

    constructor(SD, $scope, $connector, $state) {
        this.SD = SD;
        this.$scope = $scope;
        this.$connector = $connector;
        this.$state = $state;
    }

    $onInit () {
        this.gridOptions = new ChangesGridOptions(this.$scope, this.$connector, this.SD.Change, this.$state);
        this.gridOptions.fetchData();
    }
}

export {ChangeListController as controller};