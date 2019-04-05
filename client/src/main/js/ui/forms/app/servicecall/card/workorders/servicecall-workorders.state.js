import template from "../../../entity-pages/card/workorders/entity-workorders.html";
import {controller} from "../../../entity-pages/card/workorders/entity-workorders.ctrl";
import {SDResolver} from "../../../sd.resolver";
import {ServiceCallGetterResolver} from "../servicecall.resolver";

const ServiceCallCardWorkordersState = {
    name: "app.servicecall.card.workorders",
    url: "/workorders",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        entityProps: {
            common: "servicecall"
        }
    },
    resolve: {
        SD: SDResolver,
        getEntity: ServiceCallGetterResolver,
    }
};

export {ServiceCallCardWorkordersState};