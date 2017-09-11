import template from "./change-view.html"
import {controller} from "./change-view.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ChangeCardViewState = {
    name: "app.change.card.view",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver
    }
};

export {ChangeCardViewState}