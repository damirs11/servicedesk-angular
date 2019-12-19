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

  constructor() { }

  ngOnInit() {
  }

  test(event: string): void {
    this.editable = event;
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
}
