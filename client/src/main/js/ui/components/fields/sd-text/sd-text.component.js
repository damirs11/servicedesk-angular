import template from "./sd-text.html"
import {SDTextController as controller} from "./sd-text.ctrl"

/**
 * Компонента для редактируемого текста
 * target {expression} - редактируемое поле объекта / переменная
 * minLength {Number} - минимальная длина текста
 * maxLength {Number} - максимальная длина текста
 * allow-empty {Boolean} - разрешить пустое значение
 * empty-value {String} - отображаемое значение, когда поле пустое
 * disabled {expression} - выражение, определяющее можно ли редактировать поле
 * onCommit {expression($event)} - кэлбэк при коммите.
 */
const SDTextComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        minLength: "@",
        maxLength: "@",
        allowEmpty: "@",
        emptyValue: "@",
        disabled: "&",
        onCommit: "&"
    }
};

export {SDTextComponent}