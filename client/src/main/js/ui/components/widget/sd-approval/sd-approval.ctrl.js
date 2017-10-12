class SDStatusBarController {

    static $inject = ["$window","$scope","$attrs"];
    constructor($window,$scope, $attrs){
        console.log(this);
        this.$window = $window;
        this.$scope = $scope;
        this.$attrs = $attrs;
    }

    get SD(){
        return this.sd;
    }

    $onInit(){
        console.log(this.$attrs);
    }
}

export {SDStatusBarController as controller}