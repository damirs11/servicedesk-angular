import template from "../../../entity-pages/card/workorders/entity-workorders.html";
import {controller} from "../../../entity-pages/card/workorders/entity-workorders.ctrl";
import {SDResolver} from "../../../sd.resolver";

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
        getEntity: ['entity', (entity) => () => entity] // инъектим entity по имени и отдаем функцию, возвращающую entity
    }
};

export {ServiceCallCardWorkordersState};