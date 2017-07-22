class ChangeController{
    /**
     * Занят ли контроллер. Будет отображат анимацию загрузки
     * @type {string|null}
     */
    busy = null;
    /**
     * Ошибка при загрузке
     * @type {Error|null}
     */
    loadingError = null;
    /**
     * Отображаемое изменение
     * @type {SD.Change|null}
     */
    change = null;
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["SD","changeId"];
    constructor(SD,changeId){
        this.SD = SD;
        this.changeId = changeId;
        this.emptyValue = "- нет -"
    }

    $onInit(){
        this.$loadChange();
        this.loadPersons("initiators")
    }

    get loading(){
        return this.busy === "loading";
    }

    get loadingErrorIsNotFound(){
        return this.loadingError.status === 404;
    }

    get loadingErrorIsServerOffline(){
        return this.loadingError.status === -1;
    }

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline
        ;
    }

    async $loadChange(){
        if (this.busy) throw new Error("Controller is busy " + this.busy);
        try {
            this.busy = "loading";
            this.change = await new this.SD.Change(this.changeId).load();
        } catch (error) {
            this.loadingError = error || true;
        } finally {
            this.busy = null;
        }
    }

    /**
     * Подгружает персон в переменную.
     * @param varName - название переменной в контроллере
     * @param filter - фильтр поиска персон
     * @returns {Promise.<void>}
     */
    async loadPersons(varName,filter){
        this[varName] = await this.SD.Person.list(filter);
    }
}

export {ChangeController as controller}