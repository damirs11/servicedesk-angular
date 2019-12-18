import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";


@Component({
  selector: "app-sd-text",
  templateUrl: "./sd-text.component.html",
  styleUrls: ["./sd-text.component.less"]
})
export class SdTextComponent implements OnInit {
  @Input() required: boolean; // TODO: Есть в HTML, но почему-то нет в ctrl
  @Input() minlength: number;
  @Input() maxlength: number;
  @Input() allowEmpty: boolean;
  @Input() editing: boolean;
  @Input() placeholder: string;
  @Input() emptyValue: string;
  @Input() disabled: boolean;
  @Input() validate: (value: string) => string;

  enabled: boolean;
  errorMessage: any;

  private _value: string;
  @Output() valueChange = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  get value() {
    return this._value;
  }

  @Input()
  set value(value: string) {
    this._value = value;
    this.valueChange.emit(this.value);
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
      return this.errorMessage = this.validate(this.value); // TODO: Валидация не сделана
    }
    return false;
  }
}
