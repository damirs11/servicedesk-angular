import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IConfirm } from '../../dialog-interfaces/IConfirm';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.less']
})
export class ConfirmModalComponent implements OnInit {

  @Input() data: IConfirm;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
  }

}
