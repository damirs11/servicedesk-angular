import {ChangesController as controller} from "./changes.ctrl.js";
import template from "./changes.html";
import {SDResolver} from "../sd.resolver";

let ChangesState = {
    name: "app.changes",
    url: "/changes",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        SD: SDResolver
    }
};

export {ChangesState};