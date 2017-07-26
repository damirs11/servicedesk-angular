class SDTextController{
    /**
     * Положение компоненты
     * show - просто отображать текст
     * edit - предоставляет редактирование
     * @type {string}
     */
    state = "show";

    get isEditMode(){
        return this.state === "edit";
    }

    $onInit(){

    }

    onClickOut() {

    }

    edit() {
        if (this.state === "edit") return;
        this.editedValue = this.target; // Делаем редактируемое значение копией переданного
        this.state = "edit";
    }

    commit() {
        if (this.state === "show") return;

        if (this.editedValue == "") this.editedValue = null;

        if (!this.formEdit.input.$valid) {
            // ToDo что-то сделать
            console.log("Field not valid!");
            return;
        }

        const event = {
            oldValue: this.target,
            newValue: this.editedValue,
            canceled: false
        };
        this.onCommit({$event:event}); // Кидаем эвент

        if (event.canceled) return; // Если его отменили - выходим
        console.log(event);
        this.target = event.newValue; // Коммитим значение
        this.editedValue = null; // Обнуляем наше "редактируемое"
        this.state = "show";
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
        if (this.state === "show") return;
        if (!this.editedValue && !this.isAllowEmpty) {
            // ToDo запретить выход
            return;
        }
        this.editedValue = null;
        this.state = "show"
    }

    get isEditable(){
        return !this.disabled();
    }

    get isEmptyValue(){
        return this.target == null;
    }

    get isAllowEmpty(){
        if (this.allowEmpty === "" || this.allowEmpty === "true") {
            return true;
        }
        return false;
    }

    get displayValue(){
        if (this.target == null) {
            return this.emptyValue || "- нет -";
        }
        return this.target;
    }
}

export {SDTextController}