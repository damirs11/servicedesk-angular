import template from "../../../entity-pages/card/history/entity-history.html"
import {controller} from "../../../entity-pages/card/history/entity-history.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {ProblemGetterResolver} from "../problem.resolver";

const ProblemCardHistoryState = {
    name: "app.problem.card.history",
    url: "/history",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        getEntity: ProblemGetterResolver,
    }
};

export {ProblemCardHistoryState}