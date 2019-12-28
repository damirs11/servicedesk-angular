import { Component, OnInit, ChangeDetectionStrategy, Input, EventEmitter, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

const DEBOUNCE = 250;

@Component({
    selector: 'app-sd-textarea',
    templateUrl: './sd-textarea.component.html',
    styleUrls: ['./sd-textarea.component.less']
})
export class SdTextareaComponent implements OnInit {
    @Input() required: boolean; // TODO: Есть в HTML, но почему-то нет в ctrl
    @Input() minlength: number;
    @Input() maxlength: number;
    @Input() allowEmpty: boolean;
    @Input() editing: boolean;
    @Input() placeholder: string;
    @Input() emptyValue: string;
    @Input() rows: string;
    @Input() disabled: boolean;
    @Input() validate: (value: string) => string;

    private _value: string;
    @Output() valueChange = new EventEmitter<string>();
    valueChangeDebouncer: Subject<string> = new Subject<string>();

    enabled: boolean;
    errorMessage: string;

    constructor() {
        this.valueChangeDebouncer
            .pipe(debounceTime(DEBOUNCE))
            .subscribe((value) => this.valueChange.emit(value));
    }

    ngOnInit() {
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

    get isEnabled() {
        if (this.enabled === undefined) {
          return true;
        }
        return this.enabled;
    }

    get displayValue() {
        if (this.value == null) {
          return this.emptyValue || "- нет -";
        }
        return this.value;
    }

    get hasError() {
        if (this.validate) {
            return this.errorMessage = this.validate(this.value);
        }
        return false;
    }
}