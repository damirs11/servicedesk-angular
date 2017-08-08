import template from "./sd-datetime.html"
import {controller} from "./sd-datetime.ctrl"

/**
 * Компонент для выбора даты и времени
 * target {*} - редактируемое поле объекта / переменная
 * enabled {Boolean} - выражение, определяющее можно ли редактировать компонент (default: true)
 * allow-empty {Boolean} - разрешить пустое значение (default: true)
 * empty-value {@String} - отображаемое значение, когда поле пустое (default: "-нет-")
 * min-date {Date} - минимальная разрешенная дата.
 * max-date {Date} - максимальная разрешенная дата.
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
    }
};

export {SDDateTimeComponent}