import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";


@Component({
  selector: "app-sd-text",
  templateUrl: "./sd-text.component.html",
  styleUrls: ["./sd-text.component.less"]
})
export class SdTextComponent implements OnInit {
  // @Input() target: any;
  // @Input() onChange: expr;
  @Input() required: boolean; // TODO: Есть в HTML, но почему-то нет в ctrl
  @Input() minlength: number;
  @Input() maxlength: number;
  @Input() allowEmpty: boolean;
  @Input() editing: boolean;
  @Input() placeholder: string;
  @Input() emptyValue: string;
  @Input() validate: any;
  @Input() disabled: boolean;

  private _value: string;

  // Обычный геттер
  get value() {
    return this._value;
  }

  // Кастомный сеттер в котором прописан callback о смени значения
  @Input()
  set value(value: string) {
    this._value = value;
    this.valueChange.emit(this.value);
  }
  // сам callback
  @Output() valueChange = new EventEmitter<string>();

  $previousEditing: boolean;
  $previousValue: any;
  $previousTarget: any;
  commitTask: any;
  enabled: any;
  errorMessage: any;

  constructor() { }

  ngOnInit() {
    this.initCheck();
  }

  initCheck() {
    this.$previousEditing = this.editing;
    this.$previousValue = this.value;
    // this.$previousTarget = this.target;
  }

  $doCheck() {
    // if (!this.$previousEditing === this.editing ) {
    //   this.commit();
    //   this.$previousEditing = this.editing;
    // }
    // if (!this.$previousValue === this.value) {
    //   this.commit();
    //   this.$previousValue = this.value;
    // }
    // if (!this.$previousTarget === this.target) {
    //   this.commit();
    //   this.onChange();
    //   this.$previousTarget = this.target;
    // }
  }

  onChange() {
    throw new Error("Method not implemented.");
  }

  commit() {
    // if (this.commitTask) {
    //   this.$timeout.cancel(this.commitTask);
    // }

    // this.commitTask = this.$timeout(() => {
    //   if (this.value === "") {
    //     this.value = null;
    //   }

    //   this.target = this.value;
    //   this.commitTask = null;
    // }, COMMIT_DEBOUNCE);
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
    // if (!this.editing) {
    //   return this.target;
    // } // Во view-mode возвращаем значение из target
    return this.value;
  }

  get hasError() {
    if (this.validate) {
      return this.errorMessage = this.validate({ $value: this.value });
    }
    return false;
  }
}
