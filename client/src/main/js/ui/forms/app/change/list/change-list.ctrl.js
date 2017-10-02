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
        this.sortParam = searchParams['sort'];
        delete searchParams['sort'];
    }

    async $onInit () {
        this.grid = new this.$grid.ChangeGrid(this.$scope,this.SD);

        await this.configFilter();
        // this.currentFilter = this.filters[0];
        // Если мы перешли на страницу без дополнительных фильтров - задаем filter
        if (Object.keys(this.searchParams).length == 0) this.searchParams.filter = this.currentFilter
        // Вызываем у таблицы сортировку по столбцу из url
        this._setTableSort();
        this._setFilter();
        this.grid.fetchData(this.searchParams);

        this.$scope.$on("grid:double-click",::this._gridDoubleClick);
        this.$scope.$on("grid:sort-changed",::this._gridOnSortChanged);
        this.$scope.$on("grid:pagination-changed",::this._gridOnPageChanged);
    }

    _setTableSort(){
        if (!this.sortParam) return;
        const [field,direction] = this.sortParam.split("-");
        console.log({field,direction});
        this.grid.sortBy([{field,direction}])
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

    /** Параметры, которые будут отображены в url */
    get _SearchURLParams(){
        const object = Object.create(null);
        const keys = Object.keys(this.searchParams);
        for (let i in keys) {
            const key = keys[i];
            object[key] = this.searchParams[key]
        }
        object.sort = this.sortParam;
        return object;
    }

    _gridOnSortChanged(event,data){
        let sortColumns = data.sortColumns;
        sortColumns = sortColumns.map(c => `${c.field}-${c.sort.direction}`);
        this.sortParam = sortColumns[0];
        this.$location.search(this._SearchURLParams);
    }

    _gridOnPageChanged(event,data) {
        this.searchParams.page = data.page;
        this.$location.search(this._SearchURLParams);
    }

    async configFilter() {
        const filters = this.filters = [];
        filters.push(new UIEntityFilter("Назначенные мне","executor"));

        const personId = this.Session.user.person.id;
        const groups = await this.SD.Workgroup.list({person:personId});
        console.log(groups);
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
     * Поиск данных по фильтру
     * @param params
     */
    search(params) {
        this.searchParams = params;
        this.$location.search(this._SearchURLParams); // изменяет url
        console.log(`url-params`,this._SearchURLParams);
        this.grid.fetchData(this.searchParams);
    }

    /**
     * При нажатии на кнопку "Найти"
     */
    searchSubmit(text) {
        if (text == null || text == "") return;
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
        this.$state.go("app.change.card.view", {changeId: row.id});
    }
}

export {ChangeListController as controller};