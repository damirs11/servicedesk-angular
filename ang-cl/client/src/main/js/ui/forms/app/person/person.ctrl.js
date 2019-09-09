class PersonController{
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
     * Отображаемая персона
     * @type {SD.Person|null}
     */
    person = null;


    static $inject = ["SD","personId"];
    constructor(SD,personId){
        this.SD = SD;
        this.personId = personId;
    }

    $onInit(){
        this._loadPerson();
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

    async _loadPerson(){
        if (this.busy) throw new Error("Controller is busy " + this.busy);
        try {
            this.busy = "loading";
            this.person = await new this.SD.Person(this.personId).load();
        } catch (error) {
            this.loadingError = error || true;
        } finally {
            this.busy = null;
        }
    }

}

export {PersonController as controller}