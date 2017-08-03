import template from "./change-edit.html"
import {controller} from "./change-edit.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ChangeCardEditState = {
    name: "app.change.card.edit",
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

export {ChangeCardEditState}