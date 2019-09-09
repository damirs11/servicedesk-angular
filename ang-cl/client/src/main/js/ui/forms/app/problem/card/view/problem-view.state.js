import template from "./problem-view.html"
import {controller} from "./problem-view.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ProblemCardViewState = {
    name: "app.problem.card.view",
    url: "",
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

export {ProblemCardViewState}