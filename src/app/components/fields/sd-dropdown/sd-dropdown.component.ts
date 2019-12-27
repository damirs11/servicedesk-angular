import { Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { debounceTime, tap, map } from 'rxjs/operators';

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
  @Input() searchFn: (term: string, item: any) => boolean;
  @Input() validate: any; // TODO:

  @Input() fetchDataFn: () => Observable<any>;
  @Input() iconClass: (value: any) => any;
  @Input() link: (value: any) => any;

  @Input() displayValue: any;
  @Input() bindValue: string;

  firstFetch = true;
  loading = false;
  _selectedValue: any;

  enabled: boolean;
  errorMessage: string;

  values: any;

  filterFuncPassed: any;


  @Output() valueChange = new EventEmitter<any>();
  valueChangeDebouncer: Subject<any> = new Subject<any>();
  fetchDebouncer: Subject<any> = new Subject<any>();

  constructor() {

    this.valueChangeDebouncer
      .pipe(
        debounceTime(DEBOUNCE)
      )
      .subscribe((value) => {
        this.valueChange.emit(value);
      });

    this.fetchDebouncer
      .pipe(
        tap(() => this.loading = true),
        debounceTime(this.debounceTime)
      )
      .subscribe(() => {
        this.fetchDataFn().subscribe((val) => {
          this.loading = false;
          this.values = val;
          if (this.firstFetch) {
            this.firstFetch = false;
            this.selectedValue = val[0];
          }
        });
      });

    this.fetchData();
  }

  ngOnInit() {
  }

  fetchData() {
    this.fetchDebouncer.next();
  }

  display() {
    if (!this.selectedValue) {
      return;
    }
    return this.selectedValue[this.displayValue];
  }

  get selectedValue() {
    return this._selectedValue;
  }

  set selectedValue(value: any) {
    this._selectedValue = value;

    if (this.validate) {
      const validationError = this.validate(value);
      if (validationError) {
        this.errorMessage = validationError;
        return;
      }
    }
    this.valueChangeDebouncer.next(this._selectedValue);
  }

  getIconFor(value: any) {
    return this.iconClass(value);
  }

  getLinkFor(value) {
    return this.link(value);
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
    return this.iconClass || undefined;
  }

  get hasLinks() {
    return this.link || undefined;
  }

  get hasError() {
    if (this.validate) {
      return this.errorMessage = this.validate(this.selectedValue);
    }
    return false;
  }
}
