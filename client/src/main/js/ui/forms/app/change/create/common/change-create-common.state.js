// import template from "./change-create.html"
import {controller} from "./change-create-common.ctrl"

const ChangeCreateCommonState = {
    name: "app.change.create.common",
    url: "",
    controller: controller,
    template: "I am template! :O",
    controllerAs: "ctrl",
    abstract: false,
    data: {
        needAuthorize: true
    }
};

export {ChangeCreateCommonState}