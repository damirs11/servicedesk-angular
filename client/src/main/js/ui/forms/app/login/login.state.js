import {LoginController as controller} from "./login.ctrl.js";
import template from "./login.html";
import {ReturnURLResolver} from "./return-ulr.resolver";

let LoginState = {
    name: "app.login",
    url: "/login?returnUrl",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    resolve: {
        returnUrl: ReturnURLResolver
    },
    data: {
        needAuthorize: false,
    }
};

export {LoginState};