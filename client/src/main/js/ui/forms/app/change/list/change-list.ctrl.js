import {ChangesGridOptions} from './grid.config';

class ChangeListController {
    static $inject = ['SD', '$scope', '$connector', '$state', '$filter'];

    constructor(SD, $scope, $connector, $state, $filter) {
        this.SD = SD;
        this.$scope = $scope;
        this.$connector = $connector;
        this.$state = $state;
        this.$filter = $filter;
    }

    $onInit () {
        this.gridOptions = new ChangesGridOptions(this.$scope, this.$connector, this.SD.Change, this.$state, this.$filter);
        this.gridOptions.fetchData();
    }

    searchSubmit(text) {
        // ToDo вставить нужное название фильтра для быстрого поиска.
        this.gridOptions.fetchData({text});
    }
}

export {ChangeListController as controller};