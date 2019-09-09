const SMALLEST_SCREEN_WIDTH = 560;

class SDStatusBarController {

    static $inject = ["$window","$scope"];
    constructor($window,$scope){
        this.$window = $window;
        this.$scope = $scope;
    }

    getPartWidth(index) {
        if (!this.statusList) return;
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