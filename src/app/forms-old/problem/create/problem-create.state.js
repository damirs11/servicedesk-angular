import template from "./problem-create.html"
import {controller} from "./problem-create.ctrl"
import {SDResolver} from "../../sd.resolver";
import {NewProblemResolver} from "./new-problem-resolver";
import {PassedParamsResolver} from "./passed-arguments-resolver";

const ProblemCreateState = {
    name: "app.problem.create",
    url: "/create?templateId",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        problem: NewProblemResolver,
        passedParams: PassedParamsResolver
    }
};

export {ProblemCreateState}