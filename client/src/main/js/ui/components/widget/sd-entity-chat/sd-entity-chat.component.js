import template from "./sd-entity-chat.html"
import {controller} from "./sd-entity-chat.ctrl"

/**
 * Компонент для чата внутри сущностей
 * enti
 */
const SDEntityChatComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        entity: "="
    }
};

export {SDEntityChatComponent}