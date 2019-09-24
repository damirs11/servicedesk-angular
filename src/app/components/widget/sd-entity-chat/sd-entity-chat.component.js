import template from "./sd-entity-chat.html"
import {controller} from "./sd-entity-chat.ctrl"

/**
 * Компонент чата
 * @param entity {SD.EditableEntity} - сущность, внутри которой ведется чат
 * @param typeMap {MessageType[]} - описание типов сообщений
 */
const SDEntityChatComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        entity: "=",
        typeMap: "<"
    }
};

export {SDEntityChatComponent}