import {controller} from "./change-password.ctrl.js";
import template from "./change-password.html";

let ChangePasswordState = {
    name: "app.change-password",
    url: "/changePassword",
    controller: controller,
    template: template,
    controllerAs: "ctrl"
};

export {ChangePasswordState};