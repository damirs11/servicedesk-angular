import { Input, Output, EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

const DEBOUNCE = 250;
const MAX_DISPLAY_VALUES = 20;

@Component({ // TODO: Понять, чем заменить framework UI AngularJS  --- [WIP]
  selector: 'app-sd-dropdown',
  templateUrl: './sd-dropdown.component.html',
  styleUrls: ['./sd-dropdown.component.less']
})
export class SdDropdownComponent implements OnInit {

  @Input() cache: boolean;
  @Input() editing: boolean;
  @Input() debounce: number;
  @Input() disabled: boolean;
  @Input() emptyValue: string;
  @Input() allowEmpty: boolean;
  @Input() placeholder: string;
  @Input() searchEnabled: boolean;
  @Input() ignoreSameText: boolean;
  @Input() minSymbolsFetch: number;

  @Input() link: any;
  @Input() filter: any;
  @Input() iconClass: any;
  @Input() fetchData: any;
  @Input() displayValue: any;
  @Input() fetchOnChange: any;
  @Input() validate: (value: string) => string;

  $attrs: any;
  $select: any;
  $previousTarget: any;
  $previousEditing: boolean;
  $firstFetchOnChange: boolean;
  $previousSelectedValue: any;

  enabled: any;
  errorMessage: any;
  selectedValue: any;

  iconClassFuncPassed = false;
  filterFuncPassed = false;
  linkFuncPassed = false;
  values = null;
  /**
   * Последний текстовый фильтр
   */
  lastFetchRequest = null;

  private _value: string;
  @Output() valueChange = new EventEmitter<string>();
  valueChangeDebouncer: Subject<string> = new Subject<string>();

  constructor() {
    this.valueChangeDebouncer
      .pipe(debounceTime(DEBOUNCE))
      .subscribe((values) => this.valueChange.emit(values));
  }

  ngOnInit() {
    this.initCheck();
    this.selectedValue = this.value;
    this.checkPassedFunctions();
  }

  get value() {
    return this._value;
  }

  @Input()
  set value(value: string) {
    if (value !== this._value) {
      this.valueChangeDebouncer.next(value);
    }
    this._value = value;
  }

  initCheck() {
    this.$firstFetchOnChange = false;
  }

  // $onChanges(changes: { fetchOnChange: any; }) {
  //   if (changes.fetchOnChange) {
  //     // ручное открытие выпадашки с родительского контроллера
  //     // для этого прописывается для target-а $openMenu = true и прописывается fetchOnChange
  //     // fixme не умеет работать с кэшем
  //     if (this.target && this.target.$openMenu) {
  //       this.$timeout(() => {
  //         delete this.target['$openMenu'];

  //         this.fetch().then(() => {
  //           if (this.values.length) {
  //             this.$select.activate();
  //           }
  //         });

  //       });
  //     } else {
  //       if (!this.$firstFetchOnChange) {
  //         if (this.cache) { this.fetch(); }
  //         this.$firstFetchOnChange = true;
  //         return;
  //       }
  //       if (!this.cache) { this.fetch(); }
  //     }
  //   }
  // }

  $timeout(arg0: () => void) {
    throw new Error("Method not implemented.");
  }

  /**
   * Получает значения
   * @param text - текстовый фильтр для поиска
   * @param fromUI - признак того, что функция вызывается из UI а не, например, по условию fetchOnChange
   */
  async fetch(text: any, fromUI: boolean) {
    this.values = null;
    try {
      let array = await this.fetchData({ $text: text, $fromUI: fromUI });

      if (!array) {
        array = [];
      }

      array.splice(MAX_DISPLAY_VALUES, array.length);
      this.values = array;
    } catch (e) {
      throw e;
    }
  }

  /**
   * Вызывается из UI. Отделена от обычной функции fetch,
   * т.к. есть некоторые условия, при которых fetch не вызывается
   * @param text - текстовый фильтр поиска
   */
  async fetchFromUI(text) {
    if (this.cache && this.values) {
      return;
    }
    if (this.isIgnoringSameText && this.lastFetchRequest === text) {
      return;
    }
    this.fetch(text, true);
    this.lastFetchRequest = text;
  }

  checkPassedFunctions() {
    this.iconClassFuncPassed = Boolean(this.$attrs["iconClass"]);
    this.filterFuncPassed = Boolean(this.$attrs["filter"]);
    this.linkFuncPassed = Boolean(this.$attrs["link"]);
  }

  display(value: { toString: () => any; }) {
    if (value == null) {
      return this.emptyValue || "- нет -";
    }
    return this.displayValue({ $value: value }) || value.toString();
  }

  /**
   * Фильтрует значения по тексту.
   * Если есть <sd-dropdown filter=...>, выполняется функция filter
   * Иначе text ищется в toString() без учета регистра
   */
  getFilteredValues(text: string) {
    if (!this.cache) {
      return this.values;
    }
    if (!this.values) {
      return;
    }
    if (!text) {
      return this.values;
    }

    if (this.filterFuncPassed) {
      return this.values.filter(val => this.filter({$value: val, $text: text}));
    }

    return this.values.filter((val: { toString: () => string; }) => {
      return val.toString()
        .toLowerCase()
        .indexOf(text.toLowerCase()) >= 0;
    });
  }

  getIconFor(value) { // TODO: Неясно, что делает
    return this.iconClass({ $value: value });
  }

  getLinkFor(value) { // TODO: Неясно, что делает
    return this.link({ $value: value });
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
