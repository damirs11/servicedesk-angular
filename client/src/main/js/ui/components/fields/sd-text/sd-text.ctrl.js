const COMMIT_DEBOUNCE = 500;

class SDTextController{

    static $inject = ["$attrs","$scope","$timeout"];
    constructor($attrs,$scope,$timeout){
        this.$attrs = $attrs;
        this.$scope = $scope;
        this.$timeout = $timeout;
    }

    $onInit(){
        this.value = this.target;

        // Коммитим значение по дебаунсу.
        let commitTask = null;
        this.$scope.$watch(() => this.value, (newVal,oldVal) => {
            if (newVal == oldVal) return;
            if (commitTask) this.$timeout.cancel(commitTask);
            commitTask = this.$timeout(() => {
                this.commit(newVal);
                commitTask = null;
            },COMMIT_DEBOUNCE)
        })
    }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get displayValue(){
        if (this.value == null) return this.emptyValue || "- нет -";
        return this.value
    }

    commit(val){
        if (this.$attrs.validate) {
            const validationError = this.validate({
                $newValue:val,
                $oldValue:this.target
            });
            if (validationError) {
                this.validationError = validationError;
                return
            }
        }
        this.target = val
    }
}

export {SDTextController}