import { Input, Output, EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

const DEBOUNCE = 250;

@Component({
  selector: 'app-sd-number',
  templateUrl: './sd-number.component.html',
  styleUrls: ['./sd-number.component.less']
})
export class SdNumberComponent implements OnInit {

  @Input() target: number;
  @Input() onChange: any;
  @Input() min: number;
  @Input() max: number;
  @Input() step: number;
  @Input() editing: boolean;
  @Input() disabled: boolean;

  private _value: number;
  @Output() valueChange = new EventEmitter<number>();
  valueChangeDebouncer: Subject<number> = new Subject<number>();


  constructor() {
    this.valueChangeDebouncer
        .pipe(debounceTime(DEBOUNCE))
        .subscribe((value) => this.valueChange.emit(value));
  }

  ngOnInit() {
    this.step = this.step || 1;
  }

  get value() {
    return this._value;
  }

  @Input()
  set value(value: number) {
    if (isNaN(value)) {
      value = NaN;
    }

    if (value < this.min || value > this.max) {
      value = null;
    }

    if (value !== this._value) {
      this.valueChangeDebouncer.next(value);
    }
    this._value = value;
  }

  decreaseValue() {
    if (this.min != null && this.value - this.step < this.min) {
      return;
    }
    this.value = (this.value || 0) - this.step;
  }

  increaseValue() {
    if (this.max != null && this.value + this.step > this.max) {
      return;
    }
    this.value = (this.value || 0) + this.step;
  }

  commit() {
      // let value = Number(this.value);
      // if (isNaN(value)) {
      //   this.value = NaN;
      // }
      // if (value < this.min || value > this.max) {
      //   // ToDo нужно как-то подсветить ошибку и показать пользователю интервал.
      //   this.value = "";
      //   return;
      // }
      // if (value === "") this.value = null;
  }
}
