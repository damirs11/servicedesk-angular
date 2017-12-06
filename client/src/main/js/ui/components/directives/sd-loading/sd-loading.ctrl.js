class SDLoadingController {

    static $inject = ["$scope"];
    constructor($scope) {
        this.$scope = $scope;
    }

    sdLoading;
    sdLoadingSize;
    status = "success";
    value;

    $onInit() {
        this.$scope.$watch(() => this.sdLoading, (promise) => {
            if (!promise) {
                this.status = "success";
                return;
            }
            this.status = "loading";
            this.value = null;
            promise.then((val) => {
                if (this.sdLoading !== promise) return;
                this.status = "success";
                this.value = val;
            }, (val) => {
                if (this.sdLoading !== promise) return;
                this.status = "error";
                this.value = val
            })
        })
    }

    get fontSize() {
        return (this.sdLoadingSize || 50) + "px";
    }

    get loading() {
        return this.status == "loading";
    }
    get success() {
        return this.status == "success";
    }
    get error() {
        return this.status == "error";
    }
}

export {SDLoadingController as controller}