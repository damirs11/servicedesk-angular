import template from "./sd-textarea.html"
import {controller} from "./sd-textarea.ctrl"

/**
 * Компонента для редактируемого текста
 * target {expression} - редактируемое поле объекта / переменная
 * minLength {Number} - минимальная длина текста
 * maxLength {Number} - максимальная длина текста
 * allow-empty {Boolean} - разрешить пустое значение
 * empty-value {String} - отображаемое значение, когда поле пустое
 * editing {boolean} - режим редактирования
 * onCommit {expression($event)} - кэлбэк при коммите.
 * rows {Number} - количество строк
 * validate {expression} - функция-валидатор. Если result(validationError)==true - выражение не закоммитится
 * disabled {Boolean} - состояние в режиме редактирования. true - поле будет серым и недоступным.
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
        editing: "<",
        placeholder: "@",
        emptyValue: "@",
        rows: "<",
        validate: "&",
        disabled: "<"
    }
};

export {SDTextareaComponent}