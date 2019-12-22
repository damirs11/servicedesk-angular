import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

const DATE_FORMAT = "dd MMMM yyyy, HH:mm";
const DEFAULT_PLACEHOLDER = "- нет -";
const DEBOUNCE = 250;

@Component({
  selector: 'app-sd-datetime',
  templateUrl: './sd-datetime.component.html',
  styleUrls: ['./sd-datetime.component.less']
})
export class SdDatetimeComponent implements OnInit {

  @Input() formattedDate: string;
  @Input() disabled: boolean;
  @Input() emptyValue: string;
  @Input() allowEmpty: boolean;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() validate: any;
  @Input() editing: boolean;
  @Input() placeholder: string;

  errorMessage: string;
  validationError: any;
  enabled: boolean;

  _selectedDate: Date;

  @Output() dateChange = new EventEmitter<string>();
  dateChangeDebouncer: Subject<string> = new Subject<string>();

  constructor(public datePipe: DatePipe) {
    this.dateChangeDebouncer
      .pipe(debounceTime(DEBOUNCE))
      .subscribe((date) => this.dateChange.emit(date));
  }

  ngOnInit() {
  }

  get selectedDate() {
    return this._selectedDate;
  }

  set selectedDate(tempDate: Date) {
    this._selectedDate = tempDate;
    this.formattedDate = this.datePipe.transform(tempDate, DATE_FORMAT);
    this.dateChangeDebouncer.next(this.formattedDate);
  }

  onKeydown($event) {
    if ($event.keyCode === 13) { // onEnter
      const parsedDate = new Date(this.formattedDate);
      if (parsedDate.toString() === "Invalid Date") {
        return;
      }

      this.selectedDate = parsedDate;
    }
  }

  // // TODO: If the value of the before-render attribute is a function,
  // //       the date time picker will call this function before rendering a new view, passing in data about the view.
  // beforeCalendarRender($view, $dates) {
  //   const minDate = this.minDate;
  //   const maxDate = this.maxDate;

  //   if (this.minDate !== undefined && this.minDate !== null) {
  //     $dates
  //       .filter(date => date.localDateValue() <= minDate.getTime())
  //       .forEach(date => date.selectable = false);
  //   }

  //   if (this.maxDate !== undefined && this.maxDate !== null) {
  //     $dates
  //       .filter(date => date.localDateValue() >= maxDate.getTime())
  //       .forEach(date => date.selectable = false);
  //   }
  // }

  // // TODO: If the value of the on-set-time attribute is a function,
  // //       the date time picker will call this function passing in the selected value and previous value.
  // onTimeSet(newDate, oldDate) {
  //   if (this.validate) {
  //     const validationError = this.validate({
  //       $newValue: newDate,
  //       $oldValue: oldDate
  //     });
  //     if (validationError) {
  //       this.validationError = validationError;
  //       return;
  //     }
  //   }
  //   //this.target = newDate;
  // }

  getPlaceholder() {
    return this.placeholder || DEFAULT_PLACEHOLDER;
  }

  isAllowClear() {
    return this.editing && this.allowEmpty;
  }

  clear() {
    this.selectedDate = undefined;
    this.formattedDate = undefined;
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
