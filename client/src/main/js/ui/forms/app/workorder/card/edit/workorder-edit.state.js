import template from "./workorder-edit.html"
import {controller} from "./workorder-edit.ctrl"
import {SDResolver} from "../../../sd.resolver";

const WorkorderCardEditState = {
    name: "app.workorder.card.edit",
    url: "/edit",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver
    }
};

export {WorkorderCardEditState}