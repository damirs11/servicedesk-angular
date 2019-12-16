import template from "./ui-displayed-error.html"
import {controller} from "./ui-displayed-error.ctrl"

/**
 * Описание:
 * Окно показывающее серверную ошибку
 * Параметры:
 * @param error {UIDisplayedError} - ошибка
 */
export const UIDisplayedErrorModal = {
    name: "uiDisplayedError",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};