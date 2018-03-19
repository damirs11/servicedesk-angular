import template from "./image-popup.html";
import {controller} from "./image-popup.ctrl";

/**
 * Всплывающая картинка.
 *
 * Параметры:
 * @param url {string} - путь к картинке
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