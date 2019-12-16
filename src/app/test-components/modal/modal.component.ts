import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertModalComponent } from 'src/app/components/modal-action/dialogs/alert-modal/alert-modal.component';
import { IAlert } from "src/app/components/modal-action/dialog-interfaces/IAlert";
import { IConfirm } from 'src/app/components/modal-action/dialog-interfaces/IConfirm';
import { ConfirmModalComponent } from 'src/app/components/modal-action/dialogs/confirm-modal/confirm-modal.component';
import { IImagePopup } from 'src/app/components/modal-action/dialog-interfaces/IImage-popup';
import { ImagePopupModalComponent } from 'src/app/components/modal-action/dialogs/image-popup-modal/image-popup-modal.component';
import { IText } from 'src/app/components/modal-action/dialog-interfaces/IText';
import { TextModalComponent } from 'src/app/components/modal-action/dialogs/text-modal/text-modal.component';


@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.less']
})
export class ModalComponent implements OnInit {

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
  }

  openAlert() {
    const data: IAlert = {
      header: 'HEADER',
      msg: 'MSG_MSG_MSG_MSG_MSG_MSG_MSG_MSG'
    };
    const modalRef = this.modalService.open(AlertModalComponent);
    modalRef.componentInstance.data = data;
  }

  openConfirm(req?: boolean) {
    const data: IConfirm = {
      header: 'HEADER',
      msg: 'MSG_MSG_MSG_MSG_MSG_MSG_MSG_MSG',
      required: req
    };
    const modalRef = this.modalService.open(ConfirmModalComponent);
    modalRef.componentInstance.data = data;

    modalRef.result.then((result) => {
      console.log(result);
    }, (reason) => {
      console.log(reason);
    });
  }

  openImagePopup(singleImg?: boolean) {
    if (!singleImg) {
      var data: IImagePopup = {
        urls: ["http://i0.kym-cdn.com/photos/images/newsfeed/001/295/524/cda.jpg",
          "https://static.pexels.com/photos/349758/hummingbird-bird-birds-349758.jpeg",
          "http://www.theuiaa.org/wp-content/uploads/2016/08/uiaa-sustainability-intro-1300x600.jpg"],
        startFrom: 1
      };
    } else {
      var data: IImagePopup = {
        urls: ["http://i0.kym-cdn.com/photos/images/newsfeed/001/295/524/cda.jpg"]
      };
    }

    const modalRef = this.modalService.open(ImagePopupModalComponent);
    modalRef.componentInstance.data = data;
  }

  openImagePopupEmpty() {
    const modalRef = this.modalService.open(ImagePopupModalComponent);
  }

  openText(req?: boolean) {
    const data: IText = {
      header: "HEADER",
      placeholder: "PLACEHOLDER",
      maxLength: 20,
      required: req,
      value: "VALUE"
    };
    const modalRef = this.modalService.open(TextModalComponent);
    modalRef.componentInstance.data = data;

    modalRef.result.then((result) => {
      console.log(result);
    }, (reason) => {
      console.log(reason);
    });
  }
}
