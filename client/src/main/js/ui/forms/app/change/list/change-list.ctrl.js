import {ChangesGridOptions} from './grid.config';
import {UIEntityFilter} from "../../../../utils/ui-entity-filter";

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
        this.configFilter();
        const filter = this.currentFilter = this.filters[0];
        this.gridOptions.setSearchParams({filter});
        this.gridOptions.fetchData();
    }

    configFilter() {
        const filters = this.filters = [];
        filters.push(new UIEntityFilter("Назначенные мне","executor"));

        const groups = [];
        const groupFilter = new UIEntityFilter({name: "Назначенные группе", childs: groups});
        groups.push(new UIEntityFilter("Группа 1","group_1"));
        groups.push(new UIEntityFilter("Группа 2","group_2"));
        filters.push(groupFilter);

        filters.push(new UIEntityFilter({divider:true}));
        filters.push(new UIEntityFilter({header:"Моя роль:"}));
        filters.push(new UIEntityFilter("Согласующий","approver"));
        filters.push(new UIEntityFilter("Инициатор","initiator"));
        filters.push(new UIEntityFilter("Менеджер","manager"));

    }

    /**
     * Поиск данных по фильтру
     * @param params
     */
    search(params) {
        this.gridOptions.setSearchParams(params);
        this.gridOptions.fetchData();
    }

    searchSubmit(text) {
        // ToDo вставить нужное название фильтра для быстрого поиска.
        this.search({text});
    }

    currentFilter = undefined;
    /**
     * Применить фильтр к таблице.
     * @param filter {UIEntityFilter} - фильтр
     */
    applyFilter(filter){
        this.currentFilter = filter;
        this.search({filter:filter.value})
    }

    clickOpen(){
        const rows = this.gridOptions.getSelectedRows();
        if (rows.length != 1) return;
        const row = rows[0];
        this.$state.go("app.change.card.view", {changeId: row.id});
    }
}

export {ChangeListController as controller};