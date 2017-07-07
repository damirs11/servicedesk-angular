import {TestController as controller} from "./controller.js";
import template from "./template.html";

let TestState = {
    name: "app.test",
    url: "test",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: false
    }
};

export {TestState};