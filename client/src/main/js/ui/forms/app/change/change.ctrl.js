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

    static $inject = ["SD","changeId","$transitions","$state","ModalAction","$scope"];
    constructor(SD,changeId,$transitions,$state,ModalAction,$scope){
        this.SD = SD;
        this.changeId = changeId;
        this.emptyValue = "- нет -";
        this.$state = $state;
        this.$transitions = $transitions;
        this.ModalAction = ModalAction;
        this.$scope = $scope;
    }

    $onInit(){
        this.$loadChange();
        const removeExitHook = this.$transitions.onExit({
            from: state => state.name === this.$state.current.name,
        }, async (transition) => {
            if (!this.change || !this.change.isModified) return;
            const modalResult = await this.ModalAction.entityChanged();
            if (modalResult == 0) {
                return true;
            } else if (modalResult == 1) {
                return false;
            } else {
                // ToDo сделать сохранение модели и выйти
                return true;
            }
        } );

        this.$scope.$on("$destroy", removeExitHook)
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

    save(){
        this.change.save();
    }

    async reset(){
        const result = await this.ModalAction.confirm(this.$scope, {
            header: "Сброс изменений",
            msg: "Вы действительно хотите отменить все изменения?",
            required: true
        });
        if (result) this.change.reset()
    }
}

export {ChangeController as controller}