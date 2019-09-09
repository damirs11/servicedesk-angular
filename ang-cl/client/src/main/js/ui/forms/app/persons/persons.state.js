import {PersonsController as controller} from "./persons.ctrl.js";
import template from "./persons.html";
import {SDResolver} from "../sd.resolver";

let PersonsState = {
    name: "app.persons",
    url: "/persons",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        SD: SDResolver
    }
};

export {PersonsState};