import template from "./change-history.html"
import {controller} from "./change-history.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ChangeCardHistoryState = {
    name: "app.change.card.history",
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

export {ChangeCardHistoryState}