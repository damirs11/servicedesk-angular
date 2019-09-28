import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fields',
  templateUrl: './fields.component.html',
  styleUrls: ['./fields.component.less']
})
export class FieldsComponent implements OnInit {

  editable: string = "some value";
  notEditable: string = "not editable value";

  constructor() { }

  ngOnInit() {
  }

}
