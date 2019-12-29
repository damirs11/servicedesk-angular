import { Component, OnInit } from '@angular/core';
import { WorkorderService } from 'src/api/entity/workorder/workorder.service';
import { of } from 'rxjs';

@Component({
  selector: 'app-fields',
  templateUrl: './fields.component.html',
  styleUrls: ['./fields.component.less'],
  providers: [WorkorderService]
})
export class FieldsComponent implements OnInit {

  icons: string[] = [
    "fa fa-glass",
    "fa fa-flag",
    "fa fa-flash",
    "fa fa-folder",
    "fa fa-gamepad",
    "fa fa-gavel",
  ];

  editable = "some value";
  notEditable = "not editable value";

  firstToggler = true;
  secondToggler = true;
  thirdToggler = true;

  fourthToggler = true;
  editableToggler = true;

  editableNum: number = null;
  editableDate: string = null;
  anyValue: any = null;

  testMinDate: Date = new Date("21 December 2019, 00:00");
  testMaxDate: Date = new Date("30 December 2019, 23:59");

  colors = [
    { id: 0, color: "black", category: "hue", type: "primary", code: { rgba: [255, 255, 255, 1], hex: "#000" } },
    { id: 1, color: "white", category: "value", type: "primary", code: { rgba: [0, 0, 0, 1], hex: "#FFF" } },
    { id: 2, color: "red", category: "hue", type: "primary", code: { rgba: [255, 0, 0, 1], hex: "#FF0" } },
    { id: 3, color: "blue", category: "hue", type: "primary", code: { rgba: [0, 0, 255, 1], hex: "#00F" } },
    { id: 4, color: "yellow", category: "hue", type: "primary", code: { rgba: [255, 255, 0, 1], hex: "#FF0" } },
    { id: 5, color: "green", category: "hue", type: "secondary", code: { rgba: [0, 255, 0, 1], hex: "#0F0" } }
  ];

  constructor() {
  }

  ngOnInit() {
  }

  test(event: string): void {
    this.editable = event;
  }

  testNum(event: number): void {
    this.editableNum = event;
  }

  testDate(event: string): void {
    this.editableDate = event;
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

  dropdownEvent($event: any) {
    this.anyValue = $event;
  }

  fetchDate = () => {
    return of(this.colors);
  }

  getIconClass = (value) => {
    if (!value) {
      return;
    }
    return this.icons[value.id];
  }

  getLink = (value) => {
    if (!value) {
      return;
    }
    return "./test/dialog";
  }

  customSearchFn = (term: string, item) => {
    term = term.toLowerCase();
    console.log(term);
    return item.color.toLowerCase().indexOf(term) > -1 || item.value.toLowerCase() === term;
  }
}
