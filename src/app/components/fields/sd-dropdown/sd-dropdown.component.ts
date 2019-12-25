import { Input, Output, EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { debounceTime, take } from 'rxjs/operators';

const DEBOUNCE = 250;
const MAX_DISPLAY_VALUES = 20;

@Component({ // TODO: Понять, чем заменить framework UI AngularJS  --- [WIP]
  selector: 'app-sd-dropdown',
  templateUrl: './sd-dropdown.component.html',
  styleUrls: ['./sd-dropdown.component.less']
})
export class SdDropdownComponent implements OnInit {

  @Input() editing: boolean;
  @Input() disabled: boolean;
  @Input() allowEmpty: boolean;
  @Input() searchEnabled: boolean;
  @Input() ignoreSameText: boolean;
  @Input() emptyValue: string;
  @Input() placeholder: string;
  @Input() minSymbolsFetch: number;
  @Input() debounce: number;

  @Input() cache: any;
  @Input() link: any;
  @Input() filter: any;
  @Input() iconClass: any;
  @Input() fetchData: Observable<any>;
  @Input() displayValue: any;
  @Input() fetchOnChange: any;
  @Input() validate: (value: string) => string;

  @Input() bindValue: string;

  enabled: boolean;
  errorMessage: string;
  iconClassFuncPassed: any;
  linkFuncPassed: any;
  values: Observable<any>;
  filterFuncPassed: any;

  _selectedValue: any;

  @Output() valueChange = new EventEmitter<any>();
  valueChangeDebouncer: Subject<any> = new Subject<any>();

  constructor() {
    this.valueChangeDebouncer
      .pipe(debounceTime(DEBOUNCE))
      .subscribe((value) => this.valueChange.emit(value));
  }

  ngOnInit() {
  }

  get selectedValue() {
    return this._selectedValue;
  }

  set selectedValue(value: any) {
    this._selectedValue = value;
    this.valueChangeDebouncer.next(this._selectedValue);
  }

  /**
   * Фильтрует значения по тексту.
   * Если есть <sd-dropdown filter=...>, выполняется функция filter
   * Иначе text ищется в toString() без учета регистра
   */
  // getFilteredValues(text) {
  //   if (!this.cache) {
  //     return this.values;
  //   }
  //   if (!this.values) {
  //     return;
  //   }
  //   if (!text) {
  //     return this.values;
  //   }

  //   if (this.filterFuncPassed) {
  //     return this.values.filter(val => this.filter({ $value: val, $text: text }));
  //   }

  //   return this.values.filter(val => {
  //     return val.toString()
  //       .toLowerCase()
  //       .indexOf(text.toLowerCase()) >= 0;
  //   });
  // }

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
