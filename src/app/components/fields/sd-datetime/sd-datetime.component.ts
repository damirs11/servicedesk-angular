import { Component, OnInit, Input } from '@angular/core';
import { DatePipe } from '@angular/common';

const DATE_FORMAT = "dd MMMM yyyy, HH:mm";
const DEFAULT_PLACEHOLDER = "- нет -";
// Временная мера. Необходимо для того, чтобы на 1 странице работало несколько <sd-datetime/>
let freeDropdownId = 0;

@Component({
  selector: 'app-sd-datetime',
  templateUrl: './sd-datetime.component.html',
  styleUrls: ['./sd-datetime.component.less']
})
export class SdDatetimeComponent implements OnInit {

  // @Input() target;
  // @Input() onChange;

  @Input() disabled: boolean;
  @Input() emptyValue: string;
  @Input() allowEmpty: boolean;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() validate: any;
  @Input() editing: boolean;
  @Input() placeholder: string;

  errorMessage: string;
  selectedDate: Date;
  validationError: any;
  enabled: any;

  formattedDate = null;
  dropdownId: number;
  dateTimePickerConfig: any;

  $previousEditing: boolean;
  $previousTarget: any;
  $previousSelectedDate: Date;


  constructor(public datePipe: DatePipe) { }

  ngOnInit() {
    this.selectedDate = this.target;
    this.dropdownId = freeDropdownId++;
    this.dateTimePickerConfig = {
      startView: "day",
      minView: "minute",
      dropdownSelector: "#datetimepicker-dropdown-" + this.dropdownId
    };
    this.formattedDate = this.$formatDate(this.target);
  }

  initChecking() {
    this.$previousEditing = this.editing;
    this.$previousTarget = this.target;
    this.$previousSelectedDate = this.selectedDate;
  }

  onKeydown($event) {
    if ($event.keyCode === 13) { // onEnter
      const parsedDate = moment(this.formattedDate, DATE_FORMAT);

      if (!parsedDate.isValid()) {
        return;
      }
      const oldDate = this.selectedDate;

      this.selectedDate = parsedDate.toDate();
      this.onTimeSet(this.selectedDate, oldDate);
    }
  }

  beforeCalendarRender($view, $dates) {
    const minDate = this.minDate;
    const maxDate = this.maxDate;

    if (this.minDate !== undefined && this.minDate !== null) {
      $dates
        .filter(date => date.localDateValue() <= minDate.getTime())
        .forEach(date => date.selectable = false);
    }

    if (this.maxDate !== undefined && this.maxDate !== null) {
      $dates
        .filter(date => date.localDateValue() >= maxDate.getTime())
        .forEach(date => date.selectable = false);
    }
  }

  onTimeSet(newDate, oldDate) {
    if (this.validate) {
      const validationError = this.validate({
        $newValue: newDate,
        $oldValue: oldDate
      });
      if (validationError) {
        this.validationError = validationError;
        return;
      }
    }
    this.target = newDate;
  }


  $formatDate($date: Date) {
    if ($date == null) {
      return "";
    }
    return this.datePipe.transform(new Date(), DATE_FORMAT);
  }

  getPlaceholder() {
    return this.placeholder || DEFAULT_PLACEHOLDER;
  }

  isAllowClear() {
    return this.editing && this.allowEmpty;
  }

  clear(event) {
    event.stopPropagation();
    this.selectedDate = this.target = undefined;
  }

  get isEnabled() {
    if (this.enabled === undefined) {
      return true;
    }
    return this.enabled;
  }

  get isAllowEmpty() {
    if (this.allowEmpty === undefined) {
      return true;
    }
    return this.allowEmpty;
  }
  get isEditing() {
    if (this.editing === undefined) {
      return true;
    }
    return this.editing;
  }

  get hasError() {
    if (this.validate) {
      return this.errorMessage = this.validate(this.selectedDate);
    }
    return false;
  }
}
