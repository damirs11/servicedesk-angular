import template from "./alert.tpl.html"
import {AlertController as controller} from "./alert.ctrl"


/**
 * Параметры:
 * header {DOM} - заголовок диалогового окна
 * msg {DOM} - текст сообщения
 * style {String} - стиль диалогового окна.
 */
export const AlertModal = {
    name: "alert",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};