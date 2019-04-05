import {UIEntityFilter} from "../../../../utils/ui-entity-filter";
import {EntityTypes} from "../../../../../api/entity/util/entity-types";

class ServiceCallListController {
    static $inject = ['SD', '$scope', '$grid', '$state','Session', '$location', 'searchParams'];

    /** Критерии поиска, находящиеся в ссылке */
    urlSearchParams = {};
    /** Фильтр по умолчанию**/
    defaultSearchFilter = new UIEntityFilter({value: undefined, name: "Не выбран"});

    constructor(SD, $scope, $grid, $state, Session, $location,  searchParams) {
        this.SD = SD;
        this.$scope = $scope;
        this.$grid = $grid;
        this.$state = $state;
        this.Session = Session;
        this.$location = $location;

        this.urlSearchParams = searchParams;
    }

    async $onInit () {
        this.grid = new this.$grid.ServiceCallGrid(this.$scope,this.SD);

        await this.configFilter();
        this._setFilter();
        // Если перешли по ссылке с fulltext=..., ставим этот текст в поле
        if (this.urlSearchParams.fulltext) this.$scope.search.text = this.urlSearchParams.fulltext;
        if (this.urlSearchParams.no) this.$scope.search.no = this.urlSearchParams.no;
        this.grid.initializeSearchParams(this.urlSearchParams);
        // При изменении критериев поиска в таблице -  меняем URL
        this.gridSearchParams.on("change", (params) => {
            this.$location.search(params);
        });

        this.$scope.$on("grid:double-click",::this._gridDoubleClick);
    }

    get isCreateAllowed(){
        return this.Session.getTypeAccessRules("ServiceCall").isCreateEntityAllowed;
    }

    //todo
    clickCreateNew(template){
        if (!this.isCreateAllowed) return;
        const stateOptions = template ? {templateId: template.id} : undefined;
        this.$state.go("app.servicecall.create.common",stateOptions);
    }

    async loadTemplates(text){
        const entityId = EntityTypes.ServiceCall;
        return this.SD.Template.list({entityId, fullText:text});
    }

    get gridSearchParams(){
        return this.grid.searchParamsContainer;
    }

    _setFilter(){
        if (!this.urlSearchParams.filter) {
            this.currentFilter = this.defaultSearchFilter;
            return;
        }
        const filterName = this.urlSearchParams.filter;
        /** Рекурсивная функция, которая ищет фильтр/дочерний фильтр с переданным value */
        const findFilterChildByValue = (filter, value) => {
            if (filter.value === value) return filter;
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
        this.$state.go("app.servicecall.card.view",{serviceCallId: data.row.entity.id});
    }

    async configFilter() {
        const filters = this.filters = [];
        filters.push(this.defaultSearchFilter);
        filters.push(new UIEntityFilter("Назначенные мне","executor"));
        filters.push(new UIEntityFilter("Созданные мной","creator"));
        filters.push(new UIEntityFilter({divider: true}));
        filters.push(new UIEntityFilter("Новые","new"));
        filters.push(new UIEntityFilter({divider: true}));

        const personId = this.Session.user.person.id;
        const groups = await this.SD.Workgroup.list({personId:personId});
        if (groups && groups.length) {
            const uiGroups = groups.map(g => new UIEntityFilter(g.name,`group_${g.id}`));
            const groupFilter = new UIEntityFilter({name: "Назначенные группе", childs: uiGroups});
            filters.push(groupFilter)
        }
    }

    /**
     * При нажатии на кнопку "Найти"
     */
    searchSubmit(text, no) {
        if ((text === null || text === "") && (no === null || no === "")) {
            this.clearFulltextSearch();
            return;
        }
        this.grid.searchParamsContainer.add({fulltext:text, no:no}); // set params & auto-fetch
    }

    clearSearchParams() {
        this.gridSearchParams.clear();
        this.$scope.search.text = null; // Сбрасываем само текстовое поле.
        this.$scope.search.no = null;
        this.currentFilter = this.defaultSearchFilter;
    }
    /**
     * При нажатии на кнопку "Сбросить"
     */
    clearFulltextSearch() {
        this.$scope.search.text = null; // Сбрасываем само текстовое поле.
        this.$scope.search.no = null;
        if (this.gridSearchParams.fulltext === undefined && this.gridSearchParams.no === undefined) return;
        this.gridSearchParams.add({fulltext:undefined, no:undefined}); // set params & auto-fetch
    }

    currentFilter = undefined;
    /**
     * Применить фильтр к таблице.
     * @param filter {UIEntityFilter} - фильтр
     */
    applyFilter(filter){
        this.currentFilter = filter;
        this.gridSearchParams.add({filter:filter.value}); // set params & auto-fetch
    }

    clickOpen(){
        const rows = this.grid.getSelectedRows();
        if (rows.length !== 1) return;
        const row = rows[0];
        this.$state.go("app.servicecall.card.view", {serviceCallId: row.id});
    }
}

export {ServiceCallListController as controller};