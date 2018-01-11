/**
 * Журнальная форма нарядов.
 */
import {controller} from "./workorder-list.ctrl.js";
import template from "./workorder-list.html";
import {SearchParamsResolver} from "./search-params.resolver";

const WorkorderListState = {
    name: "app.workorder.list",
    url: "?fulltext&sort&filter",
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

export {WorkorderListState};