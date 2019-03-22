import {ServicecallsController as controller} from "./servicecalls.ctrl.js";
import template from "./servicecalls.html";
import {SDResolver} from "../sd.resolver";

let ServicecallsState = {
    name: "app.servicecalls",
    url: "/servicecalls",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        SD: SDResolver
    }
};

export {ServicecallsState};