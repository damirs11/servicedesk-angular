import template from "./workorder-history.html"
import {controller} from "./workorder-history.ctrl"
import {SDResolver} from "../../../sd.resolver";

const WorkorderCardHistoryState = {
    name: "app.workorder.card.history",
    url: "/history",
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

export {WorkorderCardHistoryState}