import template from "./getMessage.tpl.html"
import {GetMessageController as controller} from "./getMessage.ctrl"


/**
 * Параметры:
 * header {DOM} - заголовок диалогового окна
 * placeholder {String} - placeholder, что будет размещен в input-элементе
 * required {boolean} - обязательный ввод текста
 * maxLength (Number) - максимальная длина строки
 */
export const GetMessageModal = {
    name: "getMessage",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};