import template from "text.tpl.html";
import {GetMessageController as controller} from "text.ctrl";

/**
 * Описание:
 * Окно для запроса текста
 *
 * Параметры:
 * header {DOM} - заголовок диалогового окна
 * placeholder {String} - placeholder, что будет размещен в input-элементе
 * value {String} - значение, которое будет сразу помещено в input (default "")
 * required {boolean} - обязательный ввод текста (default: false)
 * maxLength (Number) - максимальная длина строки (default: undefined)
 *
 * Возвращает:
 * {String | null} - результат ввода. Если ввод отменен - null;
 */
export const TextModal = {
    name: "getMessage",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};