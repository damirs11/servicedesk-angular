import template from "./change-create-common.html"
import {controller} from "./change-create-common.ctrl"

const ChangeCreateCommonState = {
    name: "app.change.create.common",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: false,
    data: {
        needAuthorize: true
    }
};

export {ChangeCreateCommonState}