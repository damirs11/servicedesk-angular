import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";
const MAX_DISPLAY_VALUES = 20;

@NGInjectClass()
class SDAvatarController {
    @NGInject() $attrs;
    @NGInject() $scope;
    @NGInject() $element;
    @NGInject() $timeout;

    $onInit(){
        this.fetchFromUI();
        this.autoOpenUiSelect();
        this.filterFuncPassed = Boolean(this.$attrs["filter"]);
    }

    autoOpenUiSelect(){
        this.$timeout(() => {
            const selectElement = this.$element[0].querySelector('.ui-select-container');
            const uiSelect = angular.element(selectElement).controller('uiSelect');
            uiSelect.close = _=>_;
            uiSelect.open = true;
        });
    }

    display(value){
        if (value == null) return this.emptyValue || "- нет -";
        return this.displayValue({$value:value}) || value.toString();
    }

    get buttonClasses(){
        if (this.buttonClass) return this.buttonClass;
        return "btn btn-default";
    }

    get debounceTime() {
        return this.debounce || 500;
    }

    get isIgnoringSameText() {
        if (this.ignoreSameText === undefined) return true;
        return this.ignoreSameText;
    }

    get minSymbolsToFetch(){
        return this.minSymbolsFetch === undefined ? 0 : this.minSymbolsFetch;
    }

    /**
     * Вывзвается из UI. Отделена от обычной функции fetch,
     * т.к. есть некоторые условия, при которых fetch не вызывается
     * @param text - текстовый фильтр поиска
     */
    async fetchFromUI(text){
        if (text === undefined) text = "";
        if (this.cache && this.values) return;
        if (text.length < this.minSymbolsToFetch) return;
        if (this.isIgnoringSameText && this.lastFetchRequest == text) return;
        this.fetch(text);
        this.lastFetchRequest = text
    }


    /**
     * Получает значения
     * @param text - текстовый фильтр для поиска
     */
    async fetch(text) {
        this.values = null;
        try {
            let array = await this.fetchData({$text:text});
            if (!array) array = [];
            array.splice(MAX_DISPLAY_VALUES,array.length);
            this.values = array;
        } catch (e) {
            throw e;
        }
    }

    /**
     * Фильтрует значения по тексту.
     * Если есть <sd-dropdown filter=...>, выполняется функция filter
     * Иначе text ищется в toString() без учета регистра
     */
    getFilteredValues(text){
        if (!this.values) return;
        if (!text) return this.values;

        if (this.filterFuncPassed) {
            return this.values.filter(val => this.filter({$value:val,$text:text}));
        }

        return this.values.filter(val => {
            return val.toString()
                    .toLowerCase()
                    .indexOf(text.toLowerCase()) >= 0
        })
    }

    /**
     * Выполняется при выборе элемента из списка
     */
    onSelect($item){
        this.onClickItem({$item});
    }

    onClickPrimaryButton(){
        this.onClick({});
    }
}

export {SDAvatarController as controller}