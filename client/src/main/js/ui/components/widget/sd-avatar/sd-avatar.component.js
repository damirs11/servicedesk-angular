import template from "./sd-avatar.html"
import {controller} from "./sd-avatar.ctrl"

/**
 * Компонент для чата внутри сущностей
 * enti
 */
const SDAvatarComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        url: "@",
        link: "@",
        type: "@",
        center: "<",
        size: "<",
    }
};

export {SDAvatarComponent}