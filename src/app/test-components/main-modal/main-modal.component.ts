import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AlertModalComponent } from 'src/app/components/dialogs/alert-modal/alert-modal.component';
import { Ialert } from 'src/app/components/dialog-interfaces/Ialert';
import { Iconfirm } from 'src/app/components/dialog-interfaces/Iconfirm';
import { ConfirmModalComponent } from 'src/app/components/dialogs/confirm-modal/confirm-modal.component';
import { IimagePopup } from 'src/app/components/dialog-interfaces/Iimage-popup';
import { ImagePopupModalComponent } from 'src/app/components/dialogs/image-popup-modal/image-popup-modal.component';
import { Itext } from 'src/app/components/dialog-interfaces/Itext';
import { TextModalComponent } from 'src/app/components/dialogs/text-modal/text-modal.component';


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
    };
    const modalRef = this.modalService.open(AlertModalComponent);
    modalRef.componentInstance.data = data;
  }

  openConfirm(req?: boolean) {
    const data: Iconfirm = {
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
      var data: IimagePopup = {
        urls: ["http://i0.kym-cdn.com/photos/images/newsfeed/001/295/524/cda.jpg",
          "https://static.pexels.com/photos/349758/hummingbird-bird-birds-349758.jpeg",
          "http://www.theuiaa.org/wp-content/uploads/2016/08/uiaa-sustainability-intro-1300x600.jpg"],
        startFrom: 1
      };
    } else {
      var data: IimagePopup = {
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
    const data: Itext = {
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
