class ChangeViewController{
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

    static $inject = ["SD","changeId","$transitions","$state","ModalAction","$scope","$window"];
    constructor(SD,changeId,$transitions,$state,ModalAction,$scope,$window){
        this.SD = SD;
        this.changeId = changeId;
        this.emptyValue = "- нет -";
        this.$state = $state;
        this.$transitions = $transitions;
        this.ModalAction = ModalAction;
        this.$scope = $scope;
        this.$window = $window;
    }

    $onInit(){
        this.$loadChange();

        const fromCurrentState = {
            from: state => state.name === this.$state.current.name,
        };
        const removeExitHook = this.$transitions.onExit(fromCurrentState,async () => {
            if (!this.change || !this.change.checkModified()) return;
            const modalResult = await this.ModalAction.entityChanged();
            if (modalResult == 0) {
                return true;
            } else if (modalResult == 1) {
                return false;
            } else {
                // ToDo сделать сохранение модели и выйти
                return true;
            }
        });

        const onBeforeUnload = event => {
            if (!this.change || !this.change.checkModified()) return;
            console.log("onUnload");
            event.returnValue = "Вы действительно хотите покинуть страницу? Несохраненные изменения будут утеряны."
            return event.returnValue;
        };

        window.addEventListener("beforeunload", onBeforeUnload);

        this.$scope.$on("$destroy", () => {
            removeExitHook();
            window.removeEventListener("beforeunload", onBeforeUnload)
        })
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

export {ChangeViewController as controller}