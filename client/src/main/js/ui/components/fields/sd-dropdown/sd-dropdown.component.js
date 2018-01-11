import template from "./sd-dropdown.html"
import {controller} from "./sd-dropdown.ctrl"


/**
 * Компонент для выбора из списка
 * target {*} - редактируемое поле объекта / переменная.
 * fetch-data {expr} - функция поиска данных. Переменные: $text
 * debounce {Number} - debounce для строки поиска в миллисекундах(default: 1000)
 * search-enabled {Boolean} - добавит строку поиска (default: true)
 * allow-empty {Boolean} - разрешить пустое значение (default: true)
 * placeholder {@String} - подсказка
 * display-value {expr} функция преобразования значения в строку. Переменные: $value
 * cache {Boolean} - кэшировать ли значения. fetch-data отработает лишь 1 раз.
 * filter {expr} - фильтрация по найденым значениям. Работает в паре с cache. Переменные: $text, $value
 * validate {expression} - функция-валидатор. Если Boolean(result)==true - выражение не закоммитится
 * editing {Boolean} - состояние компонента. Режим редактирования (true) или режим просмотра (false).
 */
const SDDropdownComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        fetchData: "&",
        debounce: "<",
        enabled: "<",
        searchEnabled: "<",
        allowEmpty: "<",
        placeholder: "@",
        displayValue: "&",
        cache: "<",
        filter: "&",
        emptyValue: "@",
        validate: "&",
        editing: "<",
    }
};

export {SDDropdownComponent}