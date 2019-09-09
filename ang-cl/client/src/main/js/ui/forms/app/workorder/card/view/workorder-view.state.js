import template from "./workorder-view.html"
import {controller} from "./workorder-view.ctrl"
import {SDResolver} from "../../../sd.resolver";

const WorkorderCardViewState = {
    name: "app.workorder.card.view",
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

export {WorkorderCardViewState}