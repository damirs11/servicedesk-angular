import {UIEntityFilter} from "../../../../utils/ui-entity-filter";

class WorkorderListController {
    static $inject = ['SD', '$scope', '$grid', '$state','Session'];

    constructor(SD, $scope, $grid, $state, Session) {
        this.SD = SD;
        this.$scope = $scope;
        this.$grid = $grid;
        this.$state = $state;
        this.Session = Session;
    }

    async $onInit () {
        this.grid = new this.$grid.WorkorderGrid(this.$scope,this.SD);

        await this.configFilter();
        const filter = this.currentFilter = this.filters[0];
        this.grid.fetchData({filter:filter.value});

        this.$scope.$on("grid:double-click",::this._gridDoubleClick)
    }

    _gridDoubleClick(event,data){
        this.$state.go("app.workorder.card.view",{workorderId:data.row.entity.id});
    }

    async configFilter() {
        const filters = this.filters = [];
        filters.push(new UIEntityFilter("Назначенные мне","executor"));
        filters.push(new UIEntityFilter("Созданные мной","creator"));
        filters.push(new UIEntityFilter({divider: true}));
        filters.push(new UIEntityFilter("Новые","new"));
        filters.push(new UIEntityFilter({divider: true}));

        const personId = this.Session.user.person.id;
        const groups = await this.SD.Workgroup.list({person:personId});
        console.log(groups);
        if (groups && groups.length) {
            const uiGroups = groups.map(g => new UIEntityFilter(g.name,`group_${g.id}`));
            const groupFilter = new UIEntityFilter({name: "Назначенные группе", childs: uiGroups});
            filters.push(groupFilter)
        }
    }

    /**
     * Поиск данных по фильтру
     * @param params
     */
    search(params) {
        this.grid.fetchData(params);
    }

    searchSubmit(text) {
        this.search({fulltext:text});
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
        const rows = this.grid.getSelectedRows();
        if (rows.length != 1) return;
        const row = rows[0];
        this.$state.go("app.workorder.card.view", {workorderId: row.id});
    }
}

export {WorkorderListController as controller};