import template from "../../../entity-pages/card/history/entity-history.html"
import {controller} from "../../../entity-pages/card/history/entity-history.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {ProblemClassResolver, ProblemIdResolver} from "../problem.resolver";

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
        entityId: ProblemIdResolver,
        entityClass: ProblemClassResolver
    }
};

export {ProblemCardHistoryState}