import template from "./sd-dropdown-button.html"
import {controller} from "./sd-dropdown-button.ctrl"

/**
 * Компонент для отображения аватарки
 * onClick - Функция выполняется при нажатии на основную кнопку
 * onClickItem - Функция выполняется при нажатии на элемент из выпадающего списка. Сам элемент доступен с помощью $item
 * fetchData - Функция получения данных для выпадающего списка. Работает аналогично sd-dropdown. Доступен параметр $text
 * displayValue - Функция для отображения элемента. Можно вернуть html. Доступен параметр $value
 * cache - Boolean. Закэшировать ли значения. Если true - fetch отработает лишь 1 раз
 * debounce - Минимальная задержка между fetch-ами в миллисекундах
 * ignoreSameText - Boolean. Если true, поиск не будет выполняться, пока значение текста совпадает с прошлым поиском.
 * filter - Функция фильтрации значений. Запускается для каждого значения и возвращает boolean. Доступны $value - сам элемент, и $text - текст по которому фильтруем
 * disabled - отключена ли кнопка.
 */
const SDDropdownButtonComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    transclude: true,
    bindings: {
        onClick: "&",
        onClickItem: "&",
        buttonClass: "@",
        fetchData: "&",
        displayValue: "&",
        cache: "<",
        debounce: "<",
        ignoreSameText: "<",
        filter: "&",
        disabled: "<"
    }
};

export {SDDropdownButtonComponent}