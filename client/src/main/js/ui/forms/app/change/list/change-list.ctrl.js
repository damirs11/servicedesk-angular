import {ChangesGridOptions} from './grid.config';

class ChangeListController {
    static $inject = ['SD', '$scope', '$connector', '$state', '$parse', '$timeout'];

    constructor(SD, $scope, $connector, $state, $parse, $timeout) {
        this.SD = SD;
        this.$scope = $scope;
        this.$connector = $connector;
        this.$state = $state;
        this.$parse = $parse;
        this.$timeout = $timeout;
    }

    $onInit () {
        this.gridOptions = new ChangesGridOptions(this.$scope, this.$connector, this.SD.Change, this.$state, this.$parse, this.$timeout);
        this.gridOptions.fetchData();
    }

    searchSubmit(text) {
        // ToDo вставить нужное название фильтра для быстрого поиска.
        this.gridOptions.fetchData({text});
    }

    clickOpen(){
        const rows = this.gridOptions.getSelectedRows();
        if (rows.length != 1) return;
        const row = rows[0];
        this.$state.go("app.change.card.view", {changeId: row.id});
    }
}

export {ChangeListController as controller};