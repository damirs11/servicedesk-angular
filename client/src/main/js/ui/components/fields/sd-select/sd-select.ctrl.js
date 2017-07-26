class SDSelectController{
    viewMode = true;
    loading = false;
    ready = false;
    error = false;

    /**
     * Массив данных для выбора
     * @type {Array|null}
     */
    values = null;

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
    get isEditable(){
        if (this.enabled === undefined) return true;
        return Boolean(this.enabled);
    }

    get isEmpty(){
        return this.target == null;
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

    /**
     * Выделить значение.
     */
    select(value){
        this.selectedValue = value;
    }

    async _loadValues(){
        this.loading = true;
        try {
            this.values = await this.resolveData();
        } catch (error) {
            this.error = true;
            return;
        }
        this.loading = false;
        this.ready = true;
    }



    /** Методы меняющие состояние компоненты */
    async edit() {
        if (!this.values) await this._loadValues();
        this.selectedValue = this.target; // Делаем редактируемое значение копией переданного
        this.viewMode = false;
    }

    commit(value) {
        const sValue = value || this.selectedValue;
        const event = {
            oldValue: this.target,
            newValue: sValue,
            canceled: false
        };
        this.onCommit({$event:event}); // Кидаем эвент

        if (event.canceled) return; // Если его отменили - выходим
        this.target = event.newValue; // Коммитим значение
        this.selectedValue = null; // Обнуляем наше "редактируемое"
        this.viewMode = true;
    }


    cancel() {
        this.selectedValue = null;
        this.viewMode = true;
    }

    /**
     * Пользователь нажал вне редактируемой формы
     */
    onClickOut(){
        this.cancel();
    }

    onButtonKeydown($event) {
        if ($event.keyCode == 13) {
            this.cancel();
            return false;
        }
    }
    /* Методы меняющие состояние компоненты  */
}

export {SDSelectController}