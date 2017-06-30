import template from "./changePassword.tpl.html"
import {ChangePasswordController as controller} from "./changePassword.ctrl"

export const ChangePasswordModal = {
    name: "changePassword",
    template: template,
    controller: controller,
    controllerAs: "ctrl",
};