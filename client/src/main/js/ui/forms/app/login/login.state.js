import {LoginController as controller} from "./login.ctrl.js";
import template from "./login.html";

let LoginState = {
    name: "app.login",
    url: "/login",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: false
    }
};

export {LoginState};