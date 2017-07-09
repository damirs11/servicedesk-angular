import {TestController as controller} from "./test.ctrl.js";
import template from "./test.html";

let TestState = {
    name: "app.test",
    url: "/test",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: false
    }
};

export {TestState};