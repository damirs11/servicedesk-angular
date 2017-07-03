class MainController {
    static $inject = ['SD',"$scope","ModalAction"];

    constructor(SD,$scope,ModalAction){
        this.SD = SD;
        this.$scope = $scope;
        this.ModalAction = ModalAction;
    }
}

export {MainController};