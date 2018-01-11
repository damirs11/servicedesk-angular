import template from "./sd-attach-box.html"
import {controller} from  "./sd-attach-box.ctrl"

/**
 * Компонент для загрузки вложения. Визуально схож с sd-attachment-item
 * attachment - вложение
 * Параметры
 * onAttach({$files}) - функция, вызовется при добавлении вложения
 */
const SDAttachBoxComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        onAttach: "&",
    }
};

export {SDAttachBoxComponent}