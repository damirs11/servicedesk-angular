import template from "./sd-text.html"
import {SDTextController as controller} from "./sd-text.ctrl"

/**
 * Компонента для редактируемого текста
 * target {expression} - редактируемое поле объекта / переменная
 * minLength {Number} - минимальная длина текста
 * maxLength {Number} - максимальная длина текста
 * allow-empty {Boolean} - разрешить пустое значение
 * empty-value {String} - отображаемое значение, когда поле пустое
 * onCommit {expression($event)} - кэлбэк при коммите.
 * validate {expression} - функция-валидатор. Если Boolean(result)==true - выражение не закоммитится
 * editing {Boolean} - режим редактирования.
 * disabled {Boolean} - состояние в режиме редактирования. true - поле будет серым и недоступным.
 */
const SDTextComponent = {
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
        validate: "&",
        disabled: "<"
    }
};

export {SDTextComponent}