const DATE_FORMAT = "DD MMMM YYYY, hh:mm";
const DEFAULT_PALCEHOLDER = "- нет -";
// Временная мера. Необходимо для того, чтобы на 1 странице работало несколько <sd-datetime/>
let freeDropdownId = 0;


class SDDateTimeComponent {

    static $inject = ["$scope","$attrs"];

    constructor($scope,$attrs){
        this.$scope = $scope;
        this.$attrs = $attrs;
    }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get isAllowEmpty() {
        if (this.allowEmpty === undefined) return true;
        return this.allowEmpty;
    }

    $onInit(){
        this.selectedDate = this.target;
        this.dropdownId = freeDropdownId++;
        this.dateTimePickerConfig = {
            startView: "day",
            minView: "minute",
            dropdownSelector: "#datetimepicker-dropdown-"+this.dropdownId
        };
        this.formattedDate = this.$formatDate(this.target);

        this.$scope.$watch("ctrl.selectedDate", (newDate) => {
            this.formattedDate = this.$formatDate(newDate)
        });

        this.$scope.$watch("ctrl.editing", () => {
            this.selectedDate = this.target;
        })
    }

    formattedDate = null;


    onKeydown($event) {
        if ($event.keyCode === 13) { // onEnter
            const parsedDate = moment(this.formattedDate,DATE_FORMAT);
            if ( !parsedDate.isValid() ) return;
            const oldDate = this.selectedDate;
            this.selectedDate = parsedDate.toDate();
            this.onTimeSet(this.selectedDate,oldDate)
        }
    }

    beforeCalendarRender($view, $dates){
        const minDate = this.minDate;
        const maxDate = this.maxDate;
        if (this.minDate !== undefined && this.minDate !== "") {
            $dates.filter(date => date.localDateValue() <= minDate.getTime())
                .forEach(date => date.selectable = false)
        }
        if (this.maxDate !== undefined && this.maxDate !== "") {
            $dates.filter(date => date.localDateValue() >= maxDate.getTime())
                .forEach(date => date.selectable = false)
        }
    }

    onTimeSet(newDate,oldDate){
        if (this.$attrs.validate) {
            const validationError = this.validate({
                $newValue:newDate,
                $oldValue:oldDate
            });
            if (validationError) {
                this.validationError = validationError;
                return
            }
        }
        this.target = newDate
    }

    $formatDate($date){
        if ($date == null) return "";
        return moment($date).format(DATE_FORMAT)
    }

    getPlaceholder(){
        return this.placeholder || DEFAULT_PALCEHOLDER;
    }
}

export {SDDateTimeComponent as controller};