import template from "./changePassword.tpl.html"
import {ChangePasswordController as controller} from "./changePassword.ctrl"


/**
 * Описание:
 * окно для смены пароля.
 *
 * Параметры:
 * -
 *
 * Возвращает:
 * boolean - результат изменения пароля
 */
export const ChangePasswordModal = {
    name: "changePassword",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};