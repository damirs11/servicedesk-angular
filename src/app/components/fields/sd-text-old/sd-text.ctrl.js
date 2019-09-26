const COMMIT_DEBOUNCE = 250;

class SDTextController{

    static $inject = ["$attrs","$scope","$timeout"];
    constructor($attrs,$scope,$timeout){
        this.$attrs = $attrs;
        this.$scope = $scope;
        this.$timeout = $timeout;
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

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get displayValue(){
        if (this.value == null) return this.emptyValue || "- нет -";
        if (!this.editing) return this.target; // Во view-mode возвращаем значение из target
        return this.value
    }

    commit(val){
        if (val == "") val = null;
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