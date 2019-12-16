import template from "./servicecall-create-common.html";
import {controller} from "./servicecall-create-common.ctrl";

const ServiceCallCreateCommonState = {
    name: "app.servicecall.create.common",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: false,
    data: {
        needAuthorize: true
    }
};

export {ServiceCallCreateCommonState};