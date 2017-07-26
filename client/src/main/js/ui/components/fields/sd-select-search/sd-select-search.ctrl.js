const MAX_ROWS = 10;

class SDSelectSearchController{
    static $inject = ["$timeout","$scope"];

    viewMode = true;
    loading = false;
    ready = false;
    error = false;

    constructor($timeout,$scope){
        this.$timeout = $timeout;
        this.$scope = $scope;
    }

    $onInit(){
        this.$scope.$watch(() => this.searchText,::this.onTextChange)
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
        this.foundValues = null;
        try {
            const values = await this.search({$cache: this.cachedValues, $text: text});
            values.splice(MAX_ROWS,values.length);
            this.foundValues = values;
            console.log(this.foundValues);
        } catch (e) {
            console.log("ER",e);
            this.error = true;
        }
    }

    searchTask = null;
    onTextChange(newValue) {
        console.log("BLA");
        if (this.searchTask) this.$timeout.cancel(this.searchTask);
        if (newValue == null || newValue == "") return;
        this.ready = false;
        this.searchTask = this.$timeout(async () => {
            await this.searchValues(newValue);
            if (!this.error) this.ready = true;
        },500)
    }

    /** основные действия */

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

    onClickOut(){
        if (this.searchText == "" || this.searchText == this.currentDisplay) {
            this.cancel();
            return;
        }
        // ToDo
    }
}

export {SDSelectSearchController as controller}