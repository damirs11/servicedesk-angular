import template from "./getMessage.tpl.html"
import {GetMessageController as controller} from "./getMessage.ctrl"


/**
 * Параметры:
 * header {DOM} - заголовок диалогового окна
 * placeholder {String} - placeholder, что будет размещен в input-элементе
 * labelOk {String} - текст кнопки завершения. (По умолчанию $translate(DIALOGS_OK))
 * labelClose {String} - текст кнопки отмены. (По умолчанию $translate(DIALOGS_CLOSE))
 * maxLength (Number) - максимальная длина строки (По умолчанию 256)
 * minLength (Number) - минимальная длина строки (По умолчанию 0)
 */
export const GetMessageModal = {
    name: "getMessage",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};