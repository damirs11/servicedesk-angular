const SMALLEST_SCREEN_WIDTH = 560;

class SDStatusBarController {

    static $inject = ["$window","$scope"];
    constructor($window,$scope){
        this.$window = $window;
        this.$scope = $scope;
    }

    $onInit(){
        angular.element(this.$window).bind('resize', () => {
            this.$scope.$apply();
        });
    }

    getPartWidth(index) {
        console.log("GET PART WIDTH");
        if (!this.statusList) return;
        if (this.$window.outerWidth <= SMALLEST_SCREEN_WIDTH) {
            if (this.currentStatusIndex == index) return "100%";
            return "0%"
        }
        return 100 / this.statusList.length + "%"
    }

    get currentStatusIndex(){
        const currentId = this.target.status.id;
        return this.statusList.map(status => status.id).indexOf(currentId)
    }

    /** Геттеры, отвечающие за цвет части статусбара */
    isPastPart(index){
        return this.currentStatusIndex > index
    }
    isCurrentPart(index){
        return this.currentStatusIndex == index
    }
    isFuturePart(index){
        return this.currentStatusIndex < index
    }
}

export {SDStatusBarController as controller}