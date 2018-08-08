import template from "../../../entity-pages/card/history/entity-history.html"
import {controller} from "../../../entity-pages/card/history/entity-history.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {ChangeGetterResolver} from "../change.resolver";

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
        SD: SDResolver,
        getEntity: ChangeGetterResolver
    }
};

export {ChangeCardHistoryState}