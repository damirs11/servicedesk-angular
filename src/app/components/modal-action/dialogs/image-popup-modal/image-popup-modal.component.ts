import { Component, OnInit, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IImagePopup } from '../../dialog-interfaces/IImage-popup';

@Component({
  selector: 'app-image-popup-modal',
  templateUrl: './image-popup-modal.component.html',
  styleUrls: ['./image-popup-modal.component.less']
})
export class ImagePopupModalComponent implements OnInit {

  @Input() data: IImagePopup;
  isSingleImage: boolean = true;
  currentImageIndex: number;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
    if (!this.data) {
      this.activeModal.dismiss("No images provided to image-popup");
      throw "No images provided to image-popup";
    }
    this.isSingleImage = this.data.urls.length == 1;
    this.currentImageIndex = this.data.startFrom || 0;
    if (this.currentImageIndex >= this.data.urls.length) {
      this.currentImageIndex = 0;
    }
  }

  nextImage(){
      this.currentImageIndex = (this.currentImageIndex+1) % this.data.urls.length;
  }

  prevImage(){
      this.currentImageIndex = this.currentImageIndex <= 0 ? this.data.urls.length-1 : this.currentImageIndex-1;
  }

  get currentImageUrl(){
    return this.data.urls[this.currentImageIndex];
  }

  get pagingText() {
      return `${this.currentImageIndex + 1}/${this.data.urls.length}`
  }
}
