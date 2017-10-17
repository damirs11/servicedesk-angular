const COMMIT_DEBOUNCE = 500;

class SDNumberController{

    static $inject = ["$attrs","$scope","$timeout"];
    constructor($attrs,$scope,$timeout){
        this.$attrs = $attrs;
        this.$scope = $scope;
        this.$timeout = $timeout;
    }

    $onInit(){
        this.displayValue = this.value = this.target;
        this.step = this.step || 1;
    }

    decreaseValue(){
        if (this.min != null &&
            this.value - this.step < this.min) return;
        this.value = this.value || 0;
        this.changeValue(this.value - this.step);
    }

    increaseValue(){
        if (this.max != null &&
            this.value + this.step > this.max) return;
        this.value = this.value || 0;
        this.changeValue(this.value + this.step);
    }

    /** Изменяет значение */
    changeValue(newValue) {
        const event = {
            $oldValue: this.value,
            $newValue: newValue,
            $canceled: false,
        };
        this.onChange(event);// События изменения значения
        if (event.$canceled) return;
        this.displayValue = this.target = this.value = event.$newValue;
    }

    onEnter(){
        const parsedValue = Number(this.displayValue);
        if (isNaN(parsedValue)) {
            this.displayValue = this.value; // если не распарсилось - сбрасываем
            return;
        }
        if (parsedValue < this.min || parsedValue > this.max) {
            // ToDo нужно как-то подсветить ошибку и показать пользователю интервал.
            this.displayValue = this.value;
            return;
        }
        this.changeValue(parsedValue)
    }

    inputKeyDown($event) {
        if ($event.keyCode != 13) return; // Только enter
        this.onEnter();
    }
}

export {SDNumberController}