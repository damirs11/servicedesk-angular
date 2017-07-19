import {WorkordersController as controller} from "./workorders.ctrl.js";
import template from "./workorders.html";
import {SDResolver} from "../sd.resolver";

let WorkordersState = {
    name: "app.workorders",
    url: "/workorders",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        SD: SDResolver
    }
};

export {WorkordersState};