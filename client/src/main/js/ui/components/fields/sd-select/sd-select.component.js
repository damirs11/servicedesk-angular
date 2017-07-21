import template from "./sd-select.html"
import {SDTextController as controller} from "./sd-select.ctrl"

/**
 * Компонента для редактируемого текста
 * target {expression} - редактируемое поле объекта / переменная
 * enabled {expr-Boolean} - выражение, определяющее можно ли редактировать поле
 * allow-empty {expr-Boolean} - разрешить пустое значение
 * empty-value {String} - отображаемое значение, когда поле пустое
 * values-name {String} - Строка, отвечающая за название выбираемого объекта. (Выберите %valuesName%)
 * on-commit {expression($event)} - кэлбэк при коммите.
 * resolve-data {expression} - вызовется, чтобы получить данные
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
        valuesName: "@",
        onCommit: "&",
        resolveData: "&"
    }
};

export {SDSelectComponent}