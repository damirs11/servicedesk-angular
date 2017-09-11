class ChangeCardEditController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD","changeId","ModalAction","$transitions","$state"];
    constructor($scope,SD, changeId, ModalAction, $transitions, $state){
        this.$scope = $scope;
        this.SD = SD;
        this.changeId = changeId;
        this.ModalAction = ModalAction;
        this.$transitions = $transitions;
        this.$state = $state;
        this.testValidator = (newDate,oldDate) => {
            console.log(newDate,oldDate);
            return "Не валидно"
        }
    }

    $onInit(){
        this.headerChange = new this.SD.Change(this.changeId); // Участвует в хидере сущности.
        this.change = new this.SD.Change(this.changeId);

        const fromCurrentState = {
            from: state => state.name === this.$state.current.name,
        };
        const removeExitHook = this.$transitions.onExit(fromCurrentState,async () => {
            if (!this.change || !this.change.checkModified()) return;
            const modalResult = await this.ModalAction.entityChanged();
            if (modalResult == 0) {
                await this.change.save();
                return true;
            } else if (modalResult == 1) {
                this.change.reset();
                return true;
            } else {
                return false;
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

    async saveEditing(){
        await this.change.save();
        this.$state.go("app.change.card.view");
    }

    cancelEditing(){
        this.$state.go("app.change.card.view")
    }
}

export {ChangeCardEditController as controller}