import template from "./sd-attachments.html"
import {controller} from "./sd-attachments.ctrl"

/**
 * Компонент для отображения вложений
 * target - сущность SD, у которой есть вложения
 * allowAttach - разрешить добавлять.
 * sd - объект SD. Используется для запросов и кэширования. (Пробрасывается аттрибутом, чтобы кэш совпадал)
 */
const SDAttachmentsComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "<",
        allowAttach: "<",
        sd: "<",
    }
};

export {SDAttachmentsComponent}