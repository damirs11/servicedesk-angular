class ChangeViewController {
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
     * Изменение
     * @type {SD.Workorder|null}
     */
    workorder = null;

    static $inject = ["SD","workorderId"];
    constructor(SD,changeId){
        this.SD = SD;
        this.workorderId = workorderId;
    }

    $onInit(){
        this.$loadChange();
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
}

export {ChangeViewController as controller}