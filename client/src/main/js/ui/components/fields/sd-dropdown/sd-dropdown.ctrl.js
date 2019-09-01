import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";
const MAX_DISPLAY_VALUES = 20;

@NGInjectClass()
class SDDropdownComponentController{

    @NGInject() $attrs;
    @NGInject() $scope;

    iconClassFuncPassed = false;
    filterFuncPassed = false;
    linkFuncPassed = false;
    values = null;
    /**
     * Последний текстовый фильтр
     */
    lastFetchRequest = null;

    $onInit(){
        this.selectedValue = this.target;
        // ToDo оптимизировать эти 2 watch
        this.$scope.$watch(() => this.editing, () => this.selectedValue = this.target);
        this.$scope.$watch(() => this.target, () => this.selectedValue = this.target);
        // Игнорируем первый watch, т.к. он срабатывает при инициализации
        let firstFetchOnChange = true;
        this.$scope.$watch(() => this.fetchOnChange, (val) => {
            if (firstFetchOnChange) {
                firstFetchOnChange = false;
                return;
            }
            this.fetch(this.lastFetchRequest);
        });
        if (this.cache) this.fetch();
        this.checkPassedFunctions();
    }

    /**
     * Получает значения
     * @param text - текстовый фильтр для поиска
     * @param fromUI - признак того, что функция вызывается из UI а не, например, по условию fetchOnChange
     */
    async fetch(text, fromUI) {
        this.values = null;
        try {
            let array = await this.fetchData({$text:text, $fromUI:fromUI});
            if (!array) array = [];
            array.splice(MAX_DISPLAY_VALUES,array.length);
            this.values = array;
        } catch (e) {
            throw e;
        }
    }

    /**
     * Вызывается из UI. Отделена от обычной функции fetch,
     * т.к. есть некоторые условия, при которых fetch не вызывается
     * @param text - текстовый фильтр поиска
     */
    async fetchFromUI(text){
        if (this.cache && this.values) return;
        if (this.isIgnoringSameText && this.lastFetchRequest === text) return;
        this.fetch(text, true);
        this.lastFetchRequest = text;
    }

    checkPassedFunctions() {
        this.iconClassFuncPassed = Boolean(this.$attrs["iconClass"]);
        this.filterFuncPassed = Boolean(this.$attrs["filter"]);
        this.linkFuncPassed = Boolean(this.$attrs["link"]);
    }

    display(value){
        if (value == null) return this.emptyValue || "- нет -";
        return this.displayValue({$value:value}) || value.toString();
    }

    /**
     * Фильтрует значения по тексту.
     * Если есть <sd-dropdown filter=...>, выполняется функция filter
     * Иначе text ищется в toString() без учета регистра
     */
    getFilteredValues(text){
        if (!this.cache) return this.values;
        if (!this.values) return;
        if (!text) return this.values;

        if (this.filterFuncPassed) {
            return this.values.filter(val => this.filter({$value:val,$text:text}));
        }

        return this.values.filter(val => {
            return val.toString()
                .toLowerCase()
                .indexOf(text.toLowerCase()) >= 0;
        });
    }

    getIconFor(value) {
        return this.iconClass({$value:value});
    }

    getLinkFor(value) {
        return this.link({$value: value});
    }

    getMinSymbolsToFetch(){
        return this.minSymbolsFetch === undefined ? 3 : this.minSymbolsFetch;
    }

    /**
     * Выполняется при выборе элемента из списка
     */
    onSelect($item){
        if (this.$attrs.validate) {
            const validationError = this.validate({
                $newValue:$item,
                $oldValue:this.target
            });
            if (validationError) {
                this.validationError = validationError;
                return;
            }
        }
        this.target = $item;
    }

    get debounceTime() {
        return this.debounce || 500;
    }

    /**
     * Разрешено ли пустое значение
     */
    get isAllowClear(){
        if (this.allowEmpty === undefined) return false;
        return this.allowEmpty;
    }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get isIgnoringSameText() {
        if (this.ignoreSameText === undefined) return true;
        return this.ignoreSameText;
    }

    get hasIcons(){
        return this.iconClassFuncPassed;
    }

    get hasLinks() {
        return this.linkFuncPassed;
    }

}

export {SDDropdownComponentController as controller};