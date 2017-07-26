const DATE_FORMAT = "LLL";
let freeDropdownId = 0;


class SDDateTimeComponent {

    static $inject = ["$scope"];

    constructor($scope){
        this.$scope = $scope;
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
        this.dropdownId = freeDropdownId++;
        this.dateTimePickerConfig = {
            startView: "day",
            minView: "minute",
            dropdownSelector: "#"+this.dropdownId
        };
        this.formattedDate = this.$formatDate(this.target)
    }

    formattedDate = null;

    onTimeSet(newDate){
        this.formattedDate = this.$formatDate(newDate)
    }

    onKeydown($event) {
        if ($event.keyCode === 13) { // onEnter
            const parsedDate = moment(this.formattedDate,DATE_FORMAT);
            if ( !parsedDate.isValid() ) return;
            this.target = parsedDate.toDate();
        }
    }

    beforeCalendarRender($view, $dates){
        const minDate = this.minDate;
        const maxDate = this.maxDate;
        if (this.minDate !== undefined && this.minDate !== "") {
            $dates.filter(date => date.localDateValue() < minDate.getTime())
                .forEach(date => date.selectable = false)
        }
        if (this.maxDate !== undefined && this.maxDate !== "") {
            $dates.filter(date => date.localDateValue() > maxDate.getTime())
                .forEach(date => date.selectable = false)
        }
    }

    $formatDate($date){
        if ($date == null) return this.emptyValue || "- нет -";
        return moment($date).format(DATE_FORMAT)
    }
}

export {SDDateTimeComponent as controller};