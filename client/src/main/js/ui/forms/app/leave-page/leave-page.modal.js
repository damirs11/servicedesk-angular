import template from "./leave-page.html"
import {controller} from "./leave-page.ctrl"

/**
 * Описание:
 * окно при уходе со страницы
 *
 * Параметры:
 * title - заголовок
 * text - сообщение
 * actionInfos - список кнопок
 * actionInfos.name - название кнопки
 * actionInfos.btnClass - CSS класс кнопки (необязательное)
 *
 * Возвращает:
 * number - номер нажатой кнопки. Или null, если окно просто закрыли
 */
export const LeavePageModal = {
    name: "leavePage",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};