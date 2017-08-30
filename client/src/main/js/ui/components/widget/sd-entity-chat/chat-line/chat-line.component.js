import template from "./chat-line.html"
import {controller} from "./chat-line.ctrl"

/**
 * Компонент для отображения сообщения
 * line {SD.HistoryLine} - объект сообщения
 * type - тип сообщения
 */
const ChatLineComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        line: "<",
        type: "<"
    }
};

export {ChatLineComponent}