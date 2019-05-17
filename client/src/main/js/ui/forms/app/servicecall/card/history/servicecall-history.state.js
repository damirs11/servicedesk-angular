import template from "../../../entity-pages/card/history/entity-history.html";
import {controller} from "../../../entity-pages/card/history/entity-history.ctrl";
import {SDResolver} from "../../../sd.resolver";

const ServiceCallCardHistoryState = {
    name: "app.servicecall.card.history",
    url: "/history",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        getEntity: ['entity', (entity) => () => entity]
    }
};

export {ServiceCallCardHistoryState};