import template from "./sd-datetime.html"
import {controller} from "./sd-datetime.ctrl"


const SDDateTimeComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        enabled: "<",
        emptyValue: "@",
        allowEmpty: "<",
    //    ToDo Добавить minDate и maxDate
    }
};

export {SDDateTimeComponent}