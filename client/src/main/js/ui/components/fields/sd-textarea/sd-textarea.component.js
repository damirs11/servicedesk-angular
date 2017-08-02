import template from "./sd-textarea.html"
import {controller} from "./sd-textarea.ctrl"

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
const SDTextareaComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        minlength: "<",
        maxlength: "<",
        allowEmpty: "<",
        enabled: "<",
        placeholder: "@",
        emptyValue: "@",
        rows: "<"
    }
};

export {SDTextareaComponent}