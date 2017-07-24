import template from "./sd-dropdown.html"
import {controller} from "./sd-dropdown.ctrl"


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
        allowClear: "<",
        placeholder: "@",
        displayValue: "&"
    }
};

export {SDDropdownComponent}