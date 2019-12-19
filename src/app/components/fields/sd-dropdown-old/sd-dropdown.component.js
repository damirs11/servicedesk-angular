import template from "./sd-dropdown.html"
import {controller} from "./sd-dropdown.ctrl"


/**
 * Компонент для выбора из списка
 * название {тип_данных} [стандартное значение]
 * target {*} - редактируемое поле объекта / переменная.
 * on-change (expr) - callback-функция, вызываемая при изменении значения объекта. Переменные: $value
 * fetch-data {expr} - функция поиска данных. Переменные: $text
 * display-value {expr} функция преобразования значения в строку. Переменные: $value
 * editing {Boolean} - состояние компонента. Режим редактирования (true) или режим просмотра (false).
 * disabled {Boolean} - состояние в режиме редактирования. true - поле будет серым и недоступным.
 * cache {Boolean} - кэшировать ли значения. fetch-data отработает лишь 1 раз.
 * search-enabled {Boolean} - добавит строку поиска (default: true)
 * allow-empty {Boolean} - разрешить пустое значение (default: true)
 * placeholder {@String} - подсказка для ввода
 * debounce {Number} - debounce для строки поиска в миллисекундах(default: 1000)
 * filter {expr} - фильтрация по найденым значениям. Работает в паре с cache. Переменные: $text, $value
 * validate {expression} - функция-валидатор. Если Boolean(result)==true - выражение не закоммитится
 * ignoreSameText {Boolean} [true] - не запускать fetch, если текст в поисковой строке не изменился
 * fetchOnChange {*} - запускает fetch, когда меняется значение. (работает через $scope.$watch)
 * minCharsFetch {number} [3] - компонент будет запускать fetch только при наличии в текстовом поле n символов
 * iconClass {expr} функция, что возвращает css-класс для иконки. Переменные: $value
 * link {expr} функция, что вернет ссылку. Ссылка будет только в режиме просмотра. Переменные: $value
 */
const SDDropdownComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "<",
        onChange: "&",
        fetchData: "&",
        debounce: "<",
        disabled: "<",
        searchEnabled: "<",
        allowEmpty: "<",
        placeholder: "@",
        displayValue: "&",
        cache: "<",
        filter: "&",
        emptyValue: "@",
        validate: "&",
        editing: "<",
        ignoreSameText: "<",
        fetchOnChange: "<",
        minSymbolsFetch: "<",
        iconClass: "&",
        link: "&"
    }
};

export {SDDropdownComponent}