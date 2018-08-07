import template from "../../../entity-pages/card/history/entity-history.html"
import {controller} from "../../../entity-pages/card/history/entity-history.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {WorkorderClassResolver, WorkorderIdResolver} from "../workorder.resolver";

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
        SD: SDResolver,
        entityId: WorkorderIdResolver,
        entityClass: WorkorderClassResolver
    }
};

export {WorkorderCardHistoryState}