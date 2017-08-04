import template from "./sd-status-bar.html"
import {controller} from "./sd-status-bar.ctrl"

const SDStatusBarComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        statusList: "<"
    }
};

export {SDStatusBarComponent}