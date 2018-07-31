import template from "./problem-card-workorders.html"
import {controller} from "./problem-card-workorders.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ProblemCardWorkordersState = {
    name: "app.problem.card.workorders",
    url: "/workorders",
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

export {ProblemCardWorkordersState}