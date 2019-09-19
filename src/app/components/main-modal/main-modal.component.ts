import { Component, OnInit } from '@angular/core';
import { NgxSmartModalService } from 'ngx-smart-modal';

@Component({
  selector: 'app-main-modal',
  templateUrl: './main-modal.component.html',
  styleUrls: ['./main-modal.component.less']
})
export class MainModalComponent implements OnInit {

  constructor(public ngxSmartModalService:NgxSmartModalService) { }

  ngOnInit() {
  }

}
