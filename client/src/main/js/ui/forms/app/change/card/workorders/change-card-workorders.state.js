import template from "./change-card-workorders.html"
import {controller} from "./change-card-workorders.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ChangeCardWorkordersState = {
    name: "app.change.card.workorders",
    url: "/workorders",
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

export {ChangeCardWorkordersState}