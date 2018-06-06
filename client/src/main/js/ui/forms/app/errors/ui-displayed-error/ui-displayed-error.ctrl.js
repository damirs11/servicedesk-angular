import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class UIDisplayedErrorModalController {
    @NGInject() $modalState;
    /**
     * @type {UIDisplayedError}
     */
    @NGInject() error;

    get errorBody() {
        return this.error.body;
    }

    close() {
        this.$modalState.resolve()
    }
}

export {UIDisplayedErrorModalController as controller}