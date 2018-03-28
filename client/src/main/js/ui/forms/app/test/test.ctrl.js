import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";
@NGInjectClass()
class TestController {
    @NGInject() ModalAction;

    openImagePopup(){
        this.ModalAction.imagePopup(this.$scope, {
            urls: ["http://i0.kym-cdn.com/photos/images/newsfeed/001/295/524/cda.jpg",
                "https://static.pexels.com/photos/349758/hummingbird-bird-birds-349758.jpeg",
                "http://www.theuiaa.org/wp-content/uploads/2016/08/uiaa-sustainability-intro-1300x600.jpg"]
        })
    }


}

export {TestController};