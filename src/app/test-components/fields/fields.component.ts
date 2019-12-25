import { Component, OnInit } from '@angular/core';
import { WorkorderService } from 'src/api/entity/workorder/workorder.service';
import { Observable } from 'rxjs';
import { Color } from 'src/api/entity/workorder/Color';
import { EntityService } from 'src/api/entity/entity/entity.service';

@Component({
  selector: 'app-fields',
  templateUrl: './fields.component.html',
  styleUrls: ['./fields.component.less'],
  providers: [WorkorderService]
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

  observable: Observable<Color[]>;
  data: Color[];

  testMinDate: Date = new Date("21 December 2019, 00:00");
  testMaxDate: Date = new Date("30 December 2019, 23:59");

  constructor(private service: WorkorderService) {
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

  fetchDate(): Color[] {
    this.service.getColors().subscribe(val => this.data = val);
    console.log(this.data);
    return this.data;
  }
}
