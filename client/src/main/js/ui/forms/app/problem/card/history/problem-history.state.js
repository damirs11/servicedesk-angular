import template from "./problem-history.html"
import {controller} from "./problem-history.ctrl"
import {SDResolver} from "../../../sd.resolver";

const HistoryCardHistoryState = {
    name: "app.problem.card.history",
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

export {HistoryCardHistoryState}