import template from "./change-view.html"
import {controller} from "./change-view.ctrl"

const ChangeCardViewState = {
    name: "app.change.card.view",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    }
};

export {ChangeCardViewState}