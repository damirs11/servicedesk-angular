class WorkorderCardEditController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","workorderId","ModalAction","$transitions","$state"];
    constructor($scope,SD, workorderId, ModalAction, $transitions, $state){
        this.$scope = $scope;
        this.SD = SD;
        this.workorderId = workorderId;
        this.ModalAction = ModalAction;
        this.$transitions = $transitions;
        this.$state = $state;
    }

    $onInit(){
        this.headerWor = new this.SD.Workorder(this.workorderId); // Участвует в хидере сущности.
        this.workorder = new this.SD.Workorder(this.workorderId);

        const fromCurrentState = {
            from: state => state.name === this.$state.current.name,
        };
        const removeExitHook = this.$transitions.onExit(fromCurrentState,async () => {
            if (!this.workorder || !this.workorder.checkModified()) return;
            const modalResult = await this.ModalAction.entityChanged();
            if (modalResult == 0) {
                await this.workorder.save();
                return true;
            } else if (modalResult == 1) {
                this.workorder.reset();
                return true;
            } else {
                return false;
            }
        });

        const onBeforeUnload = event => {
            if (!this.workorder || !this.workorder.checkModified()) return;
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

    async saveEditing(){
        await this.workorder.save();
        this.$state.go("app.workorder.card.view");
    }

    cancelEditing(){
        this.$state.go("app.workorder.card.view")
    }
}

export {WorkorderCardEditController as controller}