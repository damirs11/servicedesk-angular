import template from "./workorder-create-common.html"
import {controller} from "./workorder-create-common.ctrl"

const WorkorderCreateCommonState = {
    name: "app.workorder.create.common",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: false,
    data: {
        needAuthorize: true,
    }
};

export {WorkorderCreateCommonState}