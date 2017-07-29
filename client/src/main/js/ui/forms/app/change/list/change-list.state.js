import {controller} from "./change-list.ctrl.js";
import template from "./change-list.html";

let ChangeListState = {
    name: "app.change.list",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    }
};

export {ChangeListState};