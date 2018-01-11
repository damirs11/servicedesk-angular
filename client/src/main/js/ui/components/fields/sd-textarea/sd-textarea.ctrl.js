const COMMIT_DEBOUNCE = 250;

class SDTextareaController{

    static $inject = ["$attrs","$scope","$timeout"];
    constructor($attrs,$scope,$timeout){
        this.$attrs = $attrs;
        this.$scope = $scope;
        this.$timeout = $timeout;
    }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    commitTask = null;
    $onInit(){
        this.value = this.target;
        this.$scope.$watch("ctrl.editing", () => {
            if (this.commitTask) {
                this.$timeout.cancel(this.commitTask);
                this.commitTask = null;
            }
            this.value = this.target;
        });

        // Коммитим значение по дебаунсу.
        this.$scope.$watch(() => this.value, (newVal,oldVal) => {
            if (newVal == oldVal) return;
            if (this.commitTask) this.$timeout.cancel(this.commitTask);
            this.commitTask = this.$timeout(() => {
                this.commit(newVal);
                this.commitTask = null;
            },COMMIT_DEBOUNCE)
        })
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

export {SDTextareaController as controller}