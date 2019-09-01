import {UIEntityFilter} from "../../../utils/ui-entity-filter";

class BaseEntityList {

    /** Фильтр по умолчанию**/
    defaultSearchFilter = new UIEntityFilter({value:undefined,name:"Не выбран"});
    currentFilter = undefined;

    async $onInit() {
        await this.configFilter();
        this._setFilter();
        // если мы перешли по ссылке с fulltext=... - ставим этот текст в поле поиска
        if (this.urlSearchParams.fulltext) this.$scope.search.text = this.urlSearchParams.fulltext;
        if (this.urlSearchParams.no) this.$scope.search.no = this.urlSearchParams.no;

        this.grid.initializeSearchParams(this.urlSearchParams);

        this.gridSearchParams.on("change", (params) => {
            this.$location.search(params)
        });

        this.$scope.$on("grid:double-click",::this._gridDoubleClick);
    }

    async loadTemplates(text){
        const entityId = this.entityId;
        return this.SD.Template.list({entityId, fullText:text})
    }

    /**
     * Применить фильтр к таблице.
     * @param filter {UIEntityFilter} - фильтр
     */
    applyFilter(filter){
        this.currentFilter = filter;
        this.gridSearchParams.add({filter:filter.value}); // set params & auto-fetch
    }

    /**
     * При нажатии на кнопку "Сбросить"
     */
    clearFulltextSearch() {
        this.$scope.search.text = null; // Сбрасываем само текстовое поле.
        this.$scope.search.no = null;
        if (this.gridSearchParams.fulltext == undefined && this.gridSearchParams.no == undefined) return;
        this.gridSearchParams.add({fulltext:undefined, no:undefined}); // set params & auto-fetch
    }

    clearSearchParams() {
        this.gridSearchParams.clear();
        this.$scope.search.text = null; // Сбрасываем само текстовое поле.
        this.$scope.search.no = null;
        this.currentFilter = this.defaultSearchFilter;
    }

    clickCreateNew(template){
        if (!this.isCreateAllowed) return;
        const stateOptions = template ? {templateId: template.id} : undefined;
        this.$state.go(this.stateCreated,stateOptions);
    }

    clickOpen(){
        const rows = this.grid.getSelectedRows();
        if (rows.length !== 1) return;
        const row = rows[0];
        this.$state.go(this.stateView, {problemId: row.id});
    }

    /**
     * При нажатии на кнопку "Найти"
     */
    searchSubmit(text, no) {
        if ((text == null || text == "") && (no == null || no == "")) {
            this.clearFulltextSearch();
            return;
        }
        this.grid.searchParamsContainer.add({fulltext:text, no:no}); // set params & auto-fetch
    }

    _gridDoubleClick(event,data){
        this.$state.go(this.stateView,{problemId:data.row.entity.id});
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

    get gridSearchParams(){
        return this.grid.searchParamsContainer
    }

    get isCreateAllowed(){
        return this.Session.getTypeAccessRules(this.name).isCreateEntityAllowed;
    }

}

export {BaseEntityList}