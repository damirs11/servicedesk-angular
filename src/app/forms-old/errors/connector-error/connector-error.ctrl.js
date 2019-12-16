import {NGInject, NGInjectClass} from "../../../../../common/decorator/ng-inject.decorator";

@NGInjectClass()
class ConnectorErrorModalController {
    @NGInject() $modalState;
    /**
     * @type {ConnectorRespError}
     */
    @NGInject() error;

    detailsShown = false;

    showDetails() {
        this.detailsShown = true;
    }

    hideDetails() {
        this.detailsShown = false;
    }

    close() {
        this.$modalState.resolve()
    }

    get hasDetails() {
        return Boolean(this.error.responseData.details && this.error.responseData.details.length);
    }

    get mainText() {
        return this.error.responseData.text;
    }

    get detailsText() {
        return this.error.responseData.details;
    }
}

export {ConnectorErrorModalController as controller}