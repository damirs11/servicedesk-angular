import template from "./field-text.html"
import {FieldTextController as controller} from "./field-text.ctrl"

const FieldTextComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        name: "@",
        target: "="
    }
};

export {FieldTextComponent}