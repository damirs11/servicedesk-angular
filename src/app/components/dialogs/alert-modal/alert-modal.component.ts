import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Ialert } from '../../dialog-interfaces/Ialert';

@Component({
  selector: 'app-alert-modal',
  templateUrl: './alert-modal.component.html',
  styleUrls: ['./alert-modal.component.less']
})
export class AlertModalComponent implements OnInit {
  @Input() data: Ialert;
  
  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

}
