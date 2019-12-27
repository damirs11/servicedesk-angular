import { debounce } from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { WorkorderService } from 'src/api/entity/workorder/workorder.service';
import { Observable, timer } from 'rxjs';
import { Color } from 'src/api/entity/workorder/Color';
import { EntityService } from 'src/api/entity/entity/entity.service';

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

  data: Color[];

  testMinDate: Date = new Date("21 December 2019, 00:00");
  testMaxDate: Date = new Date("30 December 2019, 23:59");

  constructor(public service: WorkorderService) {
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
    return this.service.getColors();
  }

  getIconClass = (value: Color) => {
    if (!value) {
      return;
    }
    return this.icons[value.id];
  }

  getLink = (value: Color) => {
    if (!value) {
      return;
    }
    return "./test/dialog";
  }

  customSearchFn = (term: string, item: Color) => {
    term = term.toLowerCase();
    console.log(term);
    return item.color.toLowerCase().indexOf(term) > -1 || item.value.toLowerCase() === term;
  }
}
