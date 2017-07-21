class SDTextController{
    /**
     * Положение компоненты
     * view - просто отображать текст
     * loading - загружает данные.
     * edit - предоставляет редактирование
     * error - произошла ошибка
     * @type {string}
     */
    state = "view";
    /**
     * Массив данных для выбора
     * @type {Array|null}
     */
    values = null;

    /** Геттеры отвечающие за состояние компоненты */
    get isEditMode(){
        return this.state === "edit";
    }

    get isViewMode(){
        return this.state === "view";
    }

    get isLoadingMode(){
        return this.state === "loading";
    }

    get isErrorMode(){
        return this.state === "error";
    }
    /* Геттеры отвечающие за состояние компоненты */

    /** Геттеры параметров и значений */
    get isEditable(){
        if (this.enabled === undefined) return true;
        return Boolean(this.enabled);
    }

    get isEmpty(){
        return this.target == null;
    }

    get isAllowEmpty(){
        if (this.allowEmpty === "" || this.allowEmpty === "true") {
            return true;
        }
        return false;
    }

    /**
     *
     */
    display(value){
        if (value == null) {
            return this.emptyValue || "- нет -"
        }
        return value.toString() // ToDo пробрасывать в компоненту display функцию.
    }

    /**
     * Возвращает строку - текущее значение.
     * @returns {*}
     */
    get currentDisplay(){
        return this.display(this.target)
    }
    /* Геттеры параметров и значений*/

    async loadValues(){
        this.state = "loading";
        try {
            this.values = await this.resolveData()
        } catch (error) {
            this.state = "error";
            return;
        }
        this.state = "edit"
    }

    $onInit(){

    }

    /**
     * Выделить значение.
     */
    select(value){
        this.selectedValue = value;
    }

    /** Методы меняющие состояние компоненты */
    edit() {
        this.selectedValue = this.target; // Делаем редактируемое значение копией переданного
        this.state = "edit";
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
        console.log(event);
        this.target = event.newValue; // Коммитим значение
        this.selectedValue = null; // Обнуляем наше "редактируемое"
        this.state = "view";
    }

    /**
     * Пользователь нажал вне редактируемой формы
     */
    onClickOut(){
        if (!this.editedValue || !this.formEdit.input.$valid) {
            console.log("HIGHLIGHT INPUT")
        }
    }

    cancel() {
        if (!this.editedValue && !this.isAllowEmpty) {
            // ToDo запретить выход
            return;
        }
        this.editedValue = null;
        this.state = "view"
    }
    /* Методы меняющие состояние компоненты  */
}

export {SDTextController}