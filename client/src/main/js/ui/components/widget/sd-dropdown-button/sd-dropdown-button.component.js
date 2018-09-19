import template from "./sd-dropdown-button.html"
import {controller} from "./sd-dropdown-button.ctrl"

/**
 * Компонент для отображения аватарки // ToDo написать для всех параметров описание
 * onClick -
 * onClickItem -
 * fetchData -
 * displayValue -
 * cache -
 * debounce -
 */
const SDDropdownButtonComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        onClick: "&",
        onClickItem: "&",
        fetchData: "&",
        displayValue: "&",
        cache: "<",
        debounce: "<",
        ignoreSameText: "<",
        fetchOnChange: "<",
        filter: "&",
    }
};

export {SDDropdownButtonComponent}