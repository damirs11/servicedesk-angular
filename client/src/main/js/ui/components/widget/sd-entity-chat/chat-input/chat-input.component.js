import template from "./chat-input.html"
import {controller} from "./chat-input.ctrl"

/**
 * Компонент для отправки сообщения в чат.
 * @param entity {SD.EditableEntity} - сущность, внутри которой ведется чат
 */
const ChatInputComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        entity: "="
    }
};

export {ChatInputComponent}