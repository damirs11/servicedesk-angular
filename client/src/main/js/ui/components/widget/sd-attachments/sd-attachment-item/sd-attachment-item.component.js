import template from "./sd-attachment-item.html"
import {controller} from  "./sd-attachment-item.ctrl"

/**
 * Компонент для отображения одного вложения в квадратике.
 * attachment - вложение
 */
const SDAttachmentItemComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        attachment: "<"
    }
};

export {SDAttachmentItemComponent}