import template from "src/main/js/ui/forms/modal/change-password/change-password.tpl.html"
import {ChangePasswordController as controller} from "src/main/js/ui/forms/modal/change-password/change-password.ctrl"

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