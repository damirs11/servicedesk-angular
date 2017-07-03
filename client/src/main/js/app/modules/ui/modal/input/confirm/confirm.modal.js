import template from "./confirm.tpl.html"
import {ConfirmController as controller} from "./confirm.ctrl"


/**
 * Параметры:
 * header {DOM} - заголовок диалогового окна
 * msg {DOM} - текст сообщения
 * required (boolean) - Обязательный выбор между "да" и "нет". (default false)
 *
 * Возвращает:
 * {boolean | null} - результат ввода. Если ввод отменен - null;
 */
export const ConfirmModal = {
    name: "confirm",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};