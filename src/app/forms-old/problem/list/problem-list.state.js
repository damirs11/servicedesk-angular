import {controller} from "./problem-list.ctrl.js";
import template from "./problem-list.html";
import {SearchParamsResolver} from "./search-params.resolver";

let ProblemListState = {
    name: "app.problem.list",
    url: "?sort&fulltext&no&filter&paging",
    reloadOnSearch: false,
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        searchParams: SearchParamsResolver
    }
};

export {ProblemListState};