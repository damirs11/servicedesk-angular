class ChangeCardViewController{
    /**
     * Пустое значение
     * @type {string}
     */
    emptyValue = "- нет -";

    static $inject = ["$scope","SD"];
    constructor($scope,SD){
        this.$scope = $scope;
        this.SD = SD;
    }

    // $onInit(){
    //     const fromCurrentState = {
    //         from: state => state.name === this.$state.current.name,
    //     };
    //     const removeExitHook = this.$transitions.onExit(fromCurrentState,async () => {
    //         if (!this.change || !this.change.checkModified()) return;
    //         const modalResult = await this.ModalAction.entityChanged();
    //         if (modalResult == 0) {
    //             return true;
    //         } else if (modalResult == 1) {
    //             return false;
    //         } else {
    //             // ToDo сделать сохранение модели и выйти
    //             return true;
    //         }
    //     });
    //
    //     const onBeforeUnload = event => {
    //         if (!this.change || !this.change.checkModified()) return;
    //         console.log("onUnload");
    //         event.returnValue = "Вы действительно хотите покинуть страницу? Несохраненные изменения будут утеряны."
    //         return event.returnValue;
    //     };
    //
    //     window.addEventListener("beforeunload", onBeforeUnload);
    //
    //     this.$scope.$on("$destroy", () => {
    //         removeExitHook();
    //         window.removeEventListener("beforeunload", onBeforeUnload)
    //     })
    // }

    /**
     * Геттеры для значений у ChangeCard
     */
    get loading(){
        return this.$scope.$parent.ctrl.loading;
    }

    get loadingErrorIsNotFound(){
        return this.$scope.$parent.ctrl.loadingError.status === 404;
    }

    get loadingErrorIsServerOffline(){
        return this.$scope.$parent.ctrl.loadingError.status === -1;
    }

    get loadingErrorIsCustom(){
        return !this.loadingErrorIsNotFound &&
            !this.loadingErrorIsServerOffline
        ;
    }

    get change(){
        return this.$scope.$parent.ctrl.change;
    }

    get loadingError(){
        return this.$scope.$parent.ctrl.loadingError;
    }
}

export {ChangeCardViewController as controller}