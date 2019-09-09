import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

const COMMIT_DEBOUNCE = 200;

@NGInjectClass()
class SDTextareaController{

    @NGInject() $attrs;
    @NGInject() $scope;
    @NGInject() $timeout;

    commitTask = null;

    $onInit(){
        this.value = this.target;
        this.$scope.$watch(() => this.target, () => this.commitValueByTarget());
        this.$scope.$watch("ctrl.editing", () => this.commitValueByTarget());
    }

    commitValueByTarget() {
        if (this.commitTask) {
            this.$timeout.cancel(this.commitTask);
            this.commitTask = null;
        }
        this.value = this.target;
    }

    commitTarget(value){
        if (value === "") value = null;
        if (this.$attrs.validate) {
            const validationError = this.validate({
                $newValue: value,
                $oldValue: this.target
            });
            if (validationError) {
                this.validationError = validationError;
                return
            }
        }
        this.target = value;
    }

    // Коммитим значение по дебаунсу.
    onChange({$value}) {
        if (this.commitTask) this.$timeout.cancel(this.commitTask);
        this.commitTask = this.$timeout(() => {
            this.commitTarget($value);
            this.commitTask = null;
        }, COMMIT_DEBOUNCE)
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

}

export {SDTextareaController as controller}