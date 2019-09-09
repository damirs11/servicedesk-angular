import template from "./servicecall-view.html";
import {controller} from "./servicecall-view.ctrl";
import {SDResolver} from "../../../sd.resolver";

const ServiceCallCardViewState = {
    name: "app.servicecall.card.view",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
    }
};

export {ServiceCallCardViewState};