import template from "./sd-approval.html"
import {controller} from "./sd-approval.ctrl"

const SDApprovalComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        sd: "<"
    }
};

export {SDApprovalComponent}