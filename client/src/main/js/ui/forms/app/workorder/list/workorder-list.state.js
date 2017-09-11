import {controller} from "./workorder-list.ctrl.js";
import template from "./workorder-list.html";

/**
 * Журнальная форма нарядов.
 */
const WorkorderListState = {
    name: "app.workorder.list",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    }
};

export {WorkorderListState};