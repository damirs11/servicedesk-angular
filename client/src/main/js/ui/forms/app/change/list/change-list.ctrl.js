import {ChangesGridOptions} from './grid.config';

class ChangeListController {
    static $inject = ['SD', '$scope', '$connector', '$state', '$parse'];

    constructor(SD, $scope, $connector, $state, $parse) {
        this.SD = SD;
        this.$scope = $scope;
        this.$connector = $connector;
        this.$state = $state;
        this.$parse = $parse;
    }

    $onInit () {
        this.gridOptions = new ChangesGridOptions(this.$scope, this.$connector, this.SD.Change, this.$state, this.$parse);
        this.gridOptions.fetchData();
    }

    searchSubmit(text) {
        // ToDo вставить нужное название фильтра для быстрого поиска.
        this.gridOptions.fetchData({text});
    }
}

export {ChangeListController as controller};