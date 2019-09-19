import { Component } from "@angular/core";
import { NgxSmartModalService } from 'ngx-smart-modal';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.less"]
})
export class AppComponent {
  constructor(public ngxSmartModalService: NgxSmartModalService) {}

  openTest() {
    this.ngxSmartModalService.getModal('test').open()
  }

  openAlert() {
    const obj: Object = {
      prop1: 'test',
      prop2: true,
      prop3: [{a: 'a', b: 'b'}, {c: 'c', d: 'd'}],
      prop4: 327652175423
    };
    this.ngxSmartModalService.setModalData(obj,'myModal');
    this.ngxSmartModalService.open('myModal');
    console.log(this.ngxSmartModalService.getModalStack());
  }
}
