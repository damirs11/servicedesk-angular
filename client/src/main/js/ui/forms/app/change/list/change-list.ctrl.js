import {UIEntityFilter} from "../../../../utils/ui-entity-filter";

class ChangeListController {
    static $inject = ['SD', '$scope', '$grid', '$state','Session','$location','searchParams'];

    /** Текущие филтры поисках */
    searchParams = {};
    /** Текущая сортировка */
    sortParam = null;

    constructor(SD, $scope, $grid, $state, Session, $location, searchParams) {
        this.SD = SD;
        this.$scope = $scope;
        this.$grid = $grid;
        this.$state = $state;
        this.Session = Session;
        this.$location = $location;

        this.searchParams = searchParams;
    }

    async $onInit () {
        this.grid = new this.$grid.ChangeGrid(this.$scope,this.SD);

        await this.configFilter();
        this._setFilter();
        this.grid.initializeSearchParams(this.searchParams);
        // При изменении критериев поиска в таблице -  меняем URL
        this.grid.searchParamsContainer.on("change", (params) => {
            this.$location.search(params)
        });

        this.$scope.$on("grid:double-click",::this._gridDoubleClick);
    }

    _setFilter(){
        if (!this.searchParams.filter) {
            this.currentFilter = this.filters[0];
            return;
        }
        const filterName = this.searchParams.filter;
        /** Рекурсивная функция, которая ищет фильтр/дочерний фильтр с переданным value */
        const findFilterChildByValue = (filter, value) => {
            if (filter.value == value) return filter;
            const childs = filter.childs;
            if (childs) for (let i = 0; i < childs.length; i++) {
                const result = findFilterChildByValue(childs[0],value);
                if (result) return result;
            }
        };
        const foundFilters = this.filters
                                .map(_ => findFilterChildByValue(_,filterName))
                                .filter(_ => _);
        if (foundFilters[0]) this.currentFilter = foundFilters[0];
        else this.currentFilter = this.filters[0];
    }

    _gridDoubleClick(event,data){
        this.$state.go("app.change.card.view",{changeId:data.row.entity.id});
    }

    async configFilter() {
        const filters = this.filters = [];
        filters.push(new UIEntityFilter("Назначенные мне","executor"));

        const personId = this.Session.user.person.id;
        const groups = await this.SD.Workgroup.list({person:personId});
        if (groups && groups.length) {
            const uiGroups = groups.map(g => new UIEntityFilter(g.name,`group_${g.id}`));
            const groupFilter = new UIEntityFilter({name: "Назначенные группе", childs: uiGroups});
            filters.push(groupFilter)
        }

        filters.push(new UIEntityFilter({divider:true}));
        filters.push(new UIEntityFilter("Согласующий","approver"));
        filters.push(new UIEntityFilter("Инициатор","initiator"));
        filters.push(new UIEntityFilter("Менеджер","manager"));

    }

    /**
     * При нажатии на кнопку "Найти"
     */
    searchSubmit(text) {
        if (text == null || text == "") {
            this.clearFulltextSearch();
            return;
        }
        this.searchParams.fulltext = text;
        this.grid.searchParamsContainer.add({fulltext:text}); // set params & auto-fetch
    }
    /**
     * При нажатии на кнопку "Сбросить"
     */
    clearFulltextSearch() {
        this.$scope.search.text = null; // Сбрасываем само текстовое поле.
        if (this.searchParams.fulltext == undefined) return;
        this.searchParams.fulltext = undefined;
        this.grid.searchParamsContainer.add({fulltext:undefined}); // set params & auto-fetch
    }

    currentFilter = undefined;
    /**
     * Применить фильтр к таблице.
     * @param filter {UIEntityFilter} - фильтр
     */
    applyFilter(filter){
        this.currentFilter = filter;
        this.searchParams.filter = filter.value;
        this.grid.searchParamsContainer.add({filter:filter}); // set params & auto-fetch
    }

    clickOpen(){
        const rows = this.grid.getSelectedRows();
        if (rows.length != 1) return;
        const row = rows[0];
        this.$state.go("app.change.card.view", {changeId: row.id});
    }
}

export {ChangeListController as controller};