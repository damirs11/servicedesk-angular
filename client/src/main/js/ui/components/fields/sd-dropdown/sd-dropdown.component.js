import template from "./sd-dropdown.html"
import {controller} from "./sd-dropdown.ctrl"


/**
 * Компонент для выбора из списка
 * target {*} - редактируемое поле объекта / переменная
 * fetch-data {*[] | Promise } - функция поиска данных. Внутри можно использовать $text
 * enabled {Boolean} - выражение, определяющее можно ли редактировать компонент (default: true)
 * debounce {Number} - debounce для строки поиска в миллисекундах(default: 1000)
 * search-enabled {Boolean} - добавит строку поиска (default: true)
 * allow-empty {Boolean} - разрешить пустое значение (default: true)
 * placeholder {@String} - подсказка
 * display-value { ($value => {string}) } функция преобразования значения в строку. (default: $value => $value.toString())
 *
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
        displayValue: "&"
    }
};

export {SDDropdownComponent}