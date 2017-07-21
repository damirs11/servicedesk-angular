import template from "./sd-select.html"
import {SDSelectController as controller} from "./sd-select.ctrl"

/**
 * Компонента для редактируемого текста
 * target {expression} - редактируемое поле объекта / переменная
 * enabled {expr-Boolean} - выражение, определяющее можно ли редактировать поле
 * allow-empty {expr-Boolean} - разрешить пустое значение
 * empty-value {String} - отображаемое значение, когда поле пустое
 * on-commit {expression($event)} - кэлбэк при коммите.
 * resolve-data {expression} - вызовется, чтобы получить данные
 * display-value {expression} - функция отображения данных
 */
const SDSelectComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        enabled: "<",
        allowEmpty: "<",
        emptyValue: "@",
        onCommit: "&",
        resolveData: "&",
        displayValue: "&"
    }
};

export {SDSelectComponent}