import template from "./sd-datetime.html"
import {controller} from "./sd-datetime.ctrl"

/**
 * Компонент для выбора даты и времени
 * target {*} - редактируемое поле объекта / переменная
 * allow-empty {Boolean} - разрешить пустое значение (default: true)
 * empty-value {@String} - отображаемое значение, когда поле пустое (default: "-нет-")
 * min-date {Date} - минимальная разрешенная дата.
 * max-date {Date} - максимальная разрешенная дата.
 * validate {expression} - функция-валидатор. Если Boolean(result)==true - выражение не закоммитится
 * editing {boolean} - режим редактирования
 */
const SDDateTimeComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        enabled: "<",
        emptyValue: "@",
        allowEmpty: "<",
        minDate: "<",
        maxDate: "<",
        validate: "&",
        editing: "<",
    }
};

export {SDDateTimeComponent}