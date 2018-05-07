import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";
const MAX_DISPLAY_VALUES = 20;

@NGInjectClass()
class SDDropdownComponentController{

    @NGInject() $attrs;
    @NGInject() $scope;

    $onInit(){
        this.selectedValue = this.target;
        this.$scope.$watch(() => this.editing, () => {
            this.selectedValue = this.target;
        });
        // Игнорируем первый watch, т.к. он срабатывает при инициализации
        let firstFetchOnChange = true;
        this.$scope.$watch(() => this.fetchOnChange, (val) => {
            if (firstFetchOnChange) {
                firstFetchOnChange = false;
                return;
            }
            this.fetch(this.lastFetchRequest)
        });
        if (this.cache) this.fetch();
    }

    get isIgnoringSameText() {
        if (this.ignoreSameText === undefined) return true;
        return this.ignoreSameText;
    }

    get minSymbolsToFetch(){
        return this.minSymbolsFetch === undefined ? 3 : this.minSymbolsToFetch;
    }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get debounceTime() {
        return this.debounce || 500;
    }

    display(value){
        if (value == null) return this.emptyValue || "- нет -";
        return this.displayValue({$value:value}) || value.toString();
    }

    values = null;
    /**
     * Последний текстовый фильтр
     */
    lastFetchRequest = null;
    /**
     * Вывзвается из UI. Отделена от обычной функции fetch,
     * т.к. есть некоторые условия, при которых fetch не вызывается
     * @param text - текстовый фильтр поиска
     */
    async fetchFromUI(text){
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
        if (!this.cache) return this.values;
        if (!this.values) return;
        if (!text) return this.values;

        if (this.$attrs["filter"]) {
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
        if (this.$attrs.validate) {
            const validationError = this.validate({
                $newValue:$item,
                $oldValue:this.target
            });
            if (validationError) {
                this.validationError = validationError;
                return
            }
        }
        this.target = $item
    }

    /**
     * Разрешено ли пустое значение
     */
    get isAllowClear(){
        if (this.allowEmpty === undefined) return false;
        return this.allowEmpty;
    }
}

export {SDDropdownComponentController as controller};