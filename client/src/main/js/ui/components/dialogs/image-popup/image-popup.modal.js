import template from "./image-popup.html";
import {controller} from "./image-popup.ctrl";

/**
 * Всплывающая картинка.
 *
 * Параметры:
 * @param urls {string[]} - массив ссылок на изображения
 * @param startFrom {?number} - номер изображения, с которого начать. опционально.
 *
 * Возвращает:
 * @returns undefined
 */
export const ImagePopupModal = {
    name: "imagePopup",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};