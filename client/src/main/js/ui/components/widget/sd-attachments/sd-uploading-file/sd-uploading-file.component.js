import template from "./sd-uploading-file.html"
import {controller} from  "./sd-uploading-file.ctrl"

/**
 * Загружающееся вложение
 * Параметры
 * onAbort - функция, выполнится при нажатии на крестик
 * fileName - название заливаемого файла
 * percentage - процент прогресса загрузки
 */
const SDUploadingFileComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        onAbort: "&",
        fileName: "<",
        percentage: "<"
    }
};

export {SDUploadingFileComponent}