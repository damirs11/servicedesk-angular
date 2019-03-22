import {controller} from "./servicecall-list.ctrl.js";
import template from "./servicecall-list.html";
import {SearchParamsResolver} from "./search-params.resolver";

/**
 * Журнал заявок
 */
let ServiceCallListState = {
    name: "app.servicecall.list",
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

export {ServiceCallListState};