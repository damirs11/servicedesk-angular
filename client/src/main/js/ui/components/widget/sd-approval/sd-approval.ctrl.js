class SDApprovalController {
    emptyValue = "- нет -";
    mode = "view";
    loadingPromise;

    static $inject = ["$window","$scope","$attrs"];
    constructor($window,$scope, $attrs){
        this.$window = $window;
        this.$scope = $scope;
        this.$attrs = $attrs;
    }

    async $onInit(){
        this.loadingPromise = this.target.getApproval();
        this.approval = await this.loadingPromise;
    }


    get isEditMode(){
        return this.mode == "edit"
    }

    get isViewMode(){
        return this.mode == "view"
    }

    // При нажатии на кнопку "Редактировать"
    edit(){
        this.mode = "edit";
    }

    // При нажатии на кнопку "Согласовать"
    vote(){}

    // При нажатии на кнопку "Сохранить"
    async saveEditing(){
        await this.approval.save();
        this.mode = "view";
    }

    // При нажатии на кнопку "Отмена"
    cancelEditing() {
        this.approval.reset();
        this.mode = "view";
    }

    get SD(){
        return this.sd;
    }

}

export {SDApprovalController as controller}