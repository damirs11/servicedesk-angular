import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { DateButton } from 'angular-bootstrap-datetimepicker';

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
  @Input() validate: (newValue: Date) => string;
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
    if (this.onTimeSet(tempDate)) {
      this._selectedDate = tempDate;
      this.formattedDate = this.datePipe.transform(tempDate, DATE_FORMAT);
      this.dateChangeDebouncer.next(this.formattedDate);
    }
  }

  onKeydown($event: { keyCode: number; }) {
    if ($event.keyCode === 13) { // onEnter
      const parsedDate = new Date(this.formattedDate);

      if (parsedDate < this.minDate || parsedDate >= this.maxDate) {
        return;
      }
      if (parsedDate.toString() === "Invalid Date") {
        return;
      }

      this.selectedDate = parsedDate;
    }
  }

  onTimeSet(newDate: Date): boolean {
    if (this.validate) {
      const validationError = this.validate(newDate);
      if (validationError) {
        this.validationError = validationError;
        return false;
      }
    }
    return true;
  }

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

  DatePickerFilter = (dateButton: DateButton, viewName: string) => {
    if (this.minDate && this.maxDate) {
      return dateButton.value >= this.minDate.getTime() && dateButton.value <= this.maxDate.getTime();
    }
    if (this.maxDate) {
      return dateButton.value <= this.maxDate.getTime();
    }
    if (this.minDate) {
      return dateButton.value >= this.minDate.getTime();
    }
    return true;
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
