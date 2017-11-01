import template from "./sd-attachment-uploader.html"
import {controller} from  "./sd-attachment-uploader.ctrl"

/**
 * Компонент для загрузки вложения. Визуально схож с sd-attachment-item
 * attachment - вложение
 */
const SDAttachmentUploaderComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
    }
};

export {SDAttachmentUploaderComponent}