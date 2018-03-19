import {NGInject, NGInjectClass} from "../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ImagePopupController{
    @NGInject() url;
}

export {ImagePopupController as controller};