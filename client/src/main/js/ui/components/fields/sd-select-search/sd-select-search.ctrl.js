class SDSelectController{
    viewMode = true;
    loading = false;
    ready = false;
    error = false;

    $onInit(){

    }

    /** Геттеры отвечающие за состояние компоненты */
    get isEditMode(){
        return !this.viewMode
    }

    get isViewMode(){
        return this.viewMode;
    }

    get isLoading(){
        return this.loading;
    }

    get isError(){
        return this.error;
    }

    get isReady(){
        return this.ready;
    }

    /** Геттеры параметров и значений */
    get isEnabled(){
        if (this.enabled === undefined) return true;
        return Boolean(this.enabled);
    }

    get isAllowEmpty(){
        if (this.allowEmpty === undefined) return true;
        return Boolean(this.allowEmpty);
    }

    get currentDisplay(){
        return this.display(this.target)
    }

    /**
     * Возвращает строку - текущее значение.
     * @returns {*}
     */
    display(value){
        if (value == null) {
            return this.emptyValue || "- нет -"
        }
        const view = this.displayValue({$value:value});
        if (view == undefined) return value.toString();
        return view;
    }

    cachedValues = null;
    async cacheValues(){
        this.loading = true;
        try {
            this.cachedValues = await this.cache();
            this.loading = false
        } catch (ignored) {
            this.error = true;
        }
    }

    foundValues = null;
    async searchValues(text) {
        this.ready = false;
        this.foundValues = null;
        try {
            this.foundValues = await this.search({$cache: cachedValues, $text: text})
        } catch (e) {
            this.error = true;
        }
        this.ready = true;
    }

    async edit() {
        await this.cacheValues();
        this.searchText = this.currentDisplay;
        this.foundValues = this.cachedValues;
        this.viewMode = false;
        this.ready = true;
    }

    commit(value){
        const event = {
            oldValue: this.target,
            newValue: value,
            canceled: false
        };
        this.onCommit({$event:event}); // Кидаем эвент

        if (event.canceled) return; // Если его отменили - выходим
        this.target = event.newValue; // Коммитим значение
        this.foundValues = null;
        this.viewMode = true;
    }

    cancel(){
        this.searchText = null;
        this.foundValues = null;
        this.viewMode = true;
    }
}

export {SDSelectController}