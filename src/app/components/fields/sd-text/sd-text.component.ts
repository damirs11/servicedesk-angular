import { Component, OnInit, Input, OnChanges, SimpleChange, SimpleChanges, DoCheck } from "@angular/core";
import { Observable } from "rxjs";

const COMMIT_DEBOUNCE: number = 250;

@Component({
  selector: "app-sd-text",
  templateUrl: "./sd-text.component.html",
  styleUrls: ["./sd-text.component.less"]
})
export class SdTextComponent implements OnInit {
  @Input() target: any;
  @Input() minlength: number;
  @Input() maxlength: number;
  @Input() allowEmpty: boolean;
  @Input() editing: boolean;
  @Input() placeholder: string;
  @Input() emptyValue: string;
  @Input() validate: any;
  @Input() disabled: boolean;

  value: any;
  commitTask: any;
  enabled: boolean;
  validationError: any;

  constructor() {}

  ngOnInit() {
    // TODO: Разобраться зачем ниже описанный код
    this.value = this.target;
    // this.$scope.$watch("ctrl.editing", () => {
    // if (this.commitTask) {
    //     this.$timeout.cancel(this.commitTask);
    //     this.commitTask = null;
    // }
    // this.value = this.target;
    // });

    // // Коммитим значение по дебаунсу.
    // this.$scope.$watch(() => this.value, (newVal,oldVal) => {
    //   if (newVal == oldVal) return;
    //   if (this.commitTask) this.$timeout.cancel(this.commitTask);
    //   this.commitTask = this.$timeout(() => {
    //       this.commit(newVal);
    //       this.commitTask = null;
    //   },COMMIT_DEBOUNCE)
    // });
  }

  get isEnabled() {
    if (this.enabled === undefined) { return true; }
    return this.enabled;
  }

  get displayValue() {
    if (this.value == null) {
      return this.emptyValue || "- нет -";
    }
    if (!this.editing) {
      return this.target;
    } // Во view-mode возвращаем значение из target
    return this.value;
  }

  commit(val: any) {
    if(val == "") {
      val = null;
    }
    if(this.validate) {
      const validationError = this.validate({
        $newValue: val,
        $oldValue: this.target
      });
      if(validationError) {
        this.validationError = validationError;
        return;
      }
      this.target = val;
    }
  }
}
