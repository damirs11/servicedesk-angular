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
 * validate {expression} - функция-валидатор. Если Boolean(result)==true - выражение не закоммитится
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
        enabled: "<",
        placeholder: "@",
        emptyValue: "@",
        validate: "&"
    }
};

export {SDTextComponent}