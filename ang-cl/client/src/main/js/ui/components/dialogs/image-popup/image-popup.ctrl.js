import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ImagePopupController {
    @NGInject() urls;
    @NGInject() startFrom;
    @NGInject() $modalState;

    isSingleImage = true;
    currentImageIndex;

    $onInit(){
        if (!this.urls || this.urls.length == 0) {
            this.$modalState.reject("No images provided to image-popup");
            throw "No images provided to image-popup";
        }
        this.isSingleImage = this.urls.length == 1;
        this.currentImageIndex = this.startFrom || 0;
        if (this.currentImageIndex >= this.urls.length) this.currentImageIndex = 0;
    }

    get currentImageUrl(){
        return this.urls[this.currentImageIndex];
    }

    nextImage(){
        this.currentImageIndex = (this.currentImageIndex+1)%this.urls.length;
    }

    prevImage(){
        this.currentImageIndex = this.currentImageIndex<=0 ? this.urls.length-1 : this.currentImageIndex-1;
    }

    get pagingText() {
        return `${this.currentImageIndex+1}/${this.urls.length}`
    }
}


export {ImagePopupController as controller};