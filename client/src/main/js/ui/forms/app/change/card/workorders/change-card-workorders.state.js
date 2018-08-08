import template from "../../../entity-pages/card/workorders/entity-workorders.html"
import {controller} from "../../../entity-pages/card/workorders/entity-workorders.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {ChangeGetterResolver} from "../change.resolver";

const ChangeCardWorkordersState = {
    name: "app.change.card.workorders",
    url: "/workorders",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        entityProps: {
            common: "change"
        }
    },
    resolve: {
        SD: SDResolver,
        getEntity: ChangeGetterResolver,
    }
};

export {ChangeCardWorkordersState}