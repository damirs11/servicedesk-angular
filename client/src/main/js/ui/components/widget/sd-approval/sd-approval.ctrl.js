class SDStatusBarController {

    static $inject = ["$window","$scope","$attrs"];
    constructor($window,$scope, $attrs){
        this.$window = $window;
        this.$scope = $scope;
        this.$attrs = $attrs;
    }

    emptyValue = "- нет -";

    get SD(){
        return this.sd;
    }

    async $onInit(){
        this.approval = await this.target.getApproval();
    }
}

export {SDStatusBarController as controller}