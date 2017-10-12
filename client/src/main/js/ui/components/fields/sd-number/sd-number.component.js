import template from "./sd-number.html"
import {SDNumberController as controller} from "./sd-number.ctrl"

/**
 * Компонента для редактируемого числа
 * target {expression} - редактируемое поле объекта / переменная
 */
const SDNumberComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        min: "<",
        max: "<",
        step: "<",
        onChange: "&"
    }
};

export {SDNumberComponent}