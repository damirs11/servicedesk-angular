import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';

const COMMIT_DEBOUNCE = 200;

@Component({
    selector: 'app-sd-textarea',
    templateUrl: './sd-textarea.component.html',
    styleUrls: ['./sd-textarea.component.less']
})
export class SdTextareaComponent implements OnInit {
    @Input() target: any;
    @Input() minlength: number;
    @Input() maxlength: number;
    @Input() allowEmpty: boolean;
    @Input() editing: boolean;
    @Input() placeholder: string;
    @Input() emptyValue: string;
    @Input() rows: string;
    @Input() validate: any;
    @Input() disabled: boolean;

    value: any;
    commitTask = null;
    enabled: boolean;
    validationError: any;

    constructor() {
    }

    ngOnInit() {
        this.value = this.target;
        // this.$scope.$watch(() => this.target, () => this.commitValueByTarget()); //TODO
        // this.$scope.$watch("ctrl.editing", () => this.commitValueByTarget());
    }

    commitValueByTarget() {
        if (this.commitTask) {
            //this.$timeout.cancel(this.commitTask); //TODO
            this.commitTask = null;
        }
        this.value = this.target;
    }

    commitTarget(value) {
        if (value === "") value = null;
        if (this.validate) {
            const validationError = this.validate({
                $newValue: value,
                $oldValue: this.target
            });
            if (validationError) {
                this.validationError = validationError;
                return
            }
        }
        this.target = value;
    }

    // // Коммитим значение по дебаунсу.
    // onChange({ $value }) {
    //     //if (this.commitTask) this.$timeout.cancel(this.commitTask); //TODO
    //     this.commitTask = this.$timeout(() => {
    //         this.commitTarget($value);
    //         this.commitTask = null;
    //     }, COMMIT_DEBOUNCE)
    // }

    get isEnabled() {
        if (this.enabled === undefined) return true;
        return this.enabled;
    }

    get displayValue() {
        if (this.value == null) return this.emptyValue || "- нет -";
        if (!this.editing) return this.target; // Во view-mode возвращаем значение из target
        return this.value
    }

}
