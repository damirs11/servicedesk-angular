import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fields',
  templateUrl: './fields.component.html',
  styleUrls: ['./fields.component.less']
})
export class FieldsComponent implements OnInit {

  editable = "some value";
  notEditable = "not editable value";

  firstToggler = true;
  secondToggler = true;
  thirdToggler = true;

  editableNum: number = null;
  editableDate: string = null;
  anyValue: any = null;

  testMinDate: Date = new Date("21 December 2019, 00:00");
  testMaxDate: Date = new Date("30 December 2019, 23:59");

  constructor() { }

  ngOnInit() {
  }

  test(event: string): void {
    this.editable = event;
    console.log(event);
  }

  testNum(event: number): void {
    this.editableNum = event;
    console.log(event);
  }

  testDate(event: string): void {
    this.editableDate = event;
    console.log(event);
  }

  validate(value: string): string {
    if (value === "") {
      return "Ошибка";
    }
    return null;
  }

  firstChange(): void {
    this.firstToggler = !this.firstToggler;
  }

  secondChange(): void {
    this.secondToggler = !this.secondToggler;
  }

  thirdChange(): void {
    this.thirdToggler = !this.thirdToggler;
  }
}
