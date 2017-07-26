import {TestController as controller} from "./test.ctrl.js";
import template from "./test.html";
import {SDResolver} from "../sd.resolver";

let TestState = {
    name: "app.test",
    url: "/test",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    resolve: {
        SD: SDResolver
    },
    data: {
        needAuthorize: false
    }
};

export {TestState};