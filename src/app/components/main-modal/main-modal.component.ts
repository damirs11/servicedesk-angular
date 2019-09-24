import { Component, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AlertModalComponent } from '../dialogs/alert-modal/alert-modal.component';
import { Ialert } from '../dialog-interfaces/Ialert';


@Component({
  selector: 'app-main-modal',
  templateUrl: './main-modal.component.html',
  styleUrls: ['./main-modal.component.less']
})
export class MainModalComponent implements OnInit {

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
  }

  openAlert() {
    const data: Ialert = {
      header: 'HEADER',
      msg: 'MSG_MSG_MSG_MSG_MSG_MSG_MSG_MSG'
    }
    const modalRef = this.modalService.open(AlertModalComponent);
    modalRef.componentInstance.data = data;
  }
}
