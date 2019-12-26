import { Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

const DEBOUNCE = 250;

@Component({
  selector: 'app-sd-dropdown',
  templateUrl: './sd-dropdown.component.html',
  styleUrls: ['./sd-dropdown.component.less']
})
export class SdDropdownComponent implements OnInit {

  @Input() editing: boolean;
  @Input() disabled: boolean;
  @Input() allowEmpty: boolean;
  @Input() searchEnabled: boolean;
  @Input() ignoreSameText: boolean; // TODO:
  @Input() emptyValue: string;
  @Input() placeholder: string;
  @Input() minSymbolsFetch: number; // TODO:
  @Input() debounce: number;

  @Input() cache: any; // TODO:
  @Input() link: any; // TODO:
  @Input() filter: any; // TODO:
  @Input() iconClass: any; // TODO:

  @Input() fetchDataFn: () => Observable<any>;
  @Input() validate: (value: string) => string;

  @Input() displayValue: any;
  @Input() bindValue: string;

  loading = false;
  _selectedValue: any;

  enabled: boolean;
  errorMessage: string;
  iconClassFuncPassed: any;
  linkFuncPassed: any;
  values: any;
  filterFuncPassed: any;


  @Output() valueChange = new EventEmitter<any>();
  valueChangeDebouncer: Subject<any> = new Subject<any>();
  fetchDebouncer: Subject<any> = new Subject<any>();

  constructor() {
    this.valueChangeDebouncer
      .pipe(debounceTime(DEBOUNCE))
      .subscribe((value) => {
        this.valueChange.emit(value);
      });

    this.fetchDebouncer
      .pipe(debounceTime(DEBOUNCE))
      .subscribe(() => {
        this.fetchDataFn().subscribe((val) => {
          this.loading = false;
          this.values = val;
        });
      });
  }

  ngOnInit() {
  }

  fetchData(event: any) {
    console.log(event);
    this.loading = true;
    this.fetchDebouncer.next();
  }

  get selectedValue() {
    return this._selectedValue;
  }

  set selectedValue(value: any) {
    this._selectedValue = value;
    this.valueChangeDebouncer.next(this._selectedValue);
  }

  get debounceTime() {
    return this.debounce || DEBOUNCE;
  }

  /**
   * Разрешено ли пустое значение
   */
  get isAllowClear() {
    if (this.allowEmpty === undefined) {
      return false;
    }
    return this.allowEmpty;
  }

  get isEnabled() {
    if (this.enabled === undefined) {
      return true;
    }
    return this.enabled;
  }

  get isIgnoringSameText() {
    if (this.ignoreSameText === undefined) {
      return true;
    }
    return this.ignoreSameText;
  }

  get hasIcons() {
    return this.iconClassFuncPassed;
  }

  get hasLinks() {
    return this.linkFuncPassed;
  }

  get hasError() {
    if (this.validate) {
      return this.errorMessage = this.validate(this.selectedValue);
    }
    return false;
  }
}
