import template from "./sd-number.html"
import {SDNumberController as controller} from "./sd-number.ctrl"

/**
 * Компонента для редактируемого числа
 * target {expression} - редактируемое поле объекта / переменная
 * min {number} - минимальное число
 * max {number} - максимальное число
 * step {number} - шаг, который будут добавлять кнопки + и -
 * onChange {ngFunction} - функция, которая выполнится при изменении значения
 * editing - состояние редактирования, true - режим редактирования, false - режим просмотра
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
        onChange: "&",
        editing: "<",
    }
};

export {SDNumberComponent}