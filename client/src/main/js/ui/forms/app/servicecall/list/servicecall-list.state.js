import {controller} from "./servicecall-list.ctrl.js";
import template from "./servicecall-list.html";

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

/**
 * Считывает значения параметров из адресной строки
 */
SearchParamsResolver.$inject = ["$stateParams"];
function SearchParamsResolver($stateParams){
    const params = {};
    const searchParams = ["sort", "fulltext", "no", "filter"];
    searchParams.forEach(name => {
        if($stateParams[name] !== undefined) params[name] = $stateParams[name];
    });
    return params;
}

export {ServiceCallListState};