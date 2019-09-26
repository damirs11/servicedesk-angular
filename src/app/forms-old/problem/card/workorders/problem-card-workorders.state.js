import template from "../../../entity-pages/card/workorders/entity-workorders.html"
import {controller} from "../../../entity-pages/card/workorders/entity-workorders.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {ProblemGetterResolver} from "../problem.resolver";

const ProblemCardWorkordersState = {
    name: "app.problem.card.workorders",
    url: "/workorders",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        entityProps: {
            common: "problem"
        }
    },
    resolve: {
        SD: SDResolver,
        getEntity: ProblemGetterResolver,
    }
};

export {ProblemCardWorkordersState}