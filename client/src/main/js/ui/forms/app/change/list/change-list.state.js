import {controller} from "./change-list.ctrl.js";
import template from "./change-list.html";
import {SearchParamsResolver} from "./search-params.resolver";

let ChangeListState = {
    name: "app.change.list",
    url: "?sort&fulltext&filter",
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

export {ChangeListState};