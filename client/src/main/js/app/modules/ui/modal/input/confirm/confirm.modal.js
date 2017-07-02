import template from "./confirm.tpl.html"
import {ConfirmController as controller} from "./confirm.ctrl"


/**
 * Параметры:
 * header {DOM} - заголовок диалогового окна
 * msg {DOM} - текст сообщения
 * labelYes {String} - текст кнопки согласия (По умолчанию $translate(DIALOGS_YES))
 * labelNo {String} - текст кнопки несогласия (По умолчанию $translate(DIALOGS_NO))
 * labelClose {String} - текст кнопки закрытия (По умолчанию $translate(DIALOGS_CLOSE))
 */
export const ConfirmModal = {
    name: "confirm",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};