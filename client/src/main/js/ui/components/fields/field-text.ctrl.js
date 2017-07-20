class FieldTextController{
    /**
     * Положение компоненты
     * show - просто отображать текст
     * edit - предоставляет редактирование
     * @type {string}
     */
    state = "show";

    $onInit(){

    }

    get isEditMode(){
        return this.state === "edit";
    }

    edit() {
        if (this.state === "edit") return;
        this.editedValue = this.target; // Делаем редактируемое значение копией переданного
        this.state = "edit";
    }

    commit() {
        if (this.state === "show") return;
        this.target = this.editedValue; // Коммитим значение
        this.editedValue = null; // Обнуляем наше "редактируемое"
        this.state = "show";
    }

    log() {
        console.log("#####");
        return 12;

    }

    cancel() {
        if (this.state === "show") return;
        this.editedValue = null;
        this.state = "show"
    }
}

export {FieldTextController}