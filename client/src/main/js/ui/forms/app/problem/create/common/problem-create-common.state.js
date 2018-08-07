import template from "./problem-create-common.html"
import {controller} from "./problem-create-common.ctrl"

const ProblemCreateCommonState = {
    name: "app.problem.create.common",
    url: "",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: false,
    data: {
        needAuthorize: true
    }
};

export {ProblemCreateCommonState}