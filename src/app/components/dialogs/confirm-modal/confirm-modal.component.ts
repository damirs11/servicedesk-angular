import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Iconfirm } from '../../dialog-interfaces/Iconfirm';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.less']
})
export class ConfirmModalComponent implements OnInit {

  @Input() data: Iconfirm;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

}
