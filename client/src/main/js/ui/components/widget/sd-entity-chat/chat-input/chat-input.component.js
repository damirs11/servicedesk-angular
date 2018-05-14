import template from "./chat-input.html"
import {controller} from "./chat-input.ctrl"

/**
 * Компонент для отправки сообщения в чат.
 * @param send {Function} - вызовется при нажатии на отправку.
 */
const ChatInputComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        send: "&",
        types: "<",
        accessRules: "<"
    }
};

export {ChatInputComponent}