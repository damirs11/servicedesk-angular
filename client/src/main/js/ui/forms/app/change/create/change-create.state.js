import template from "./change-create.html"
import {controller} from "./change-create.ctrl"
import {SDResolver} from "../../sd.resolver";

const ChangeCreateState = {
    name: "app.change.create",
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
        change: NewChangeResolver,
        passedParams: PassedParamsResolver
    }
};

// Новое изменение внедряется зависимостью.
// Все для того, чтобы передавать без проблем его между дочерними стейтами.
NewChangeResolver.$inject = ["SD"];
function NewChangeResolver(SD) {
    const change = new SD.Change();
    change.$update({
        assignment: new SD.EntityAssignment(),
    });
    return change;
}

PassedParamsResolver.$inject = ["$stateParams"];
function PassedParamsResolver($stateParams){
    const passedParams = {
        templateId: $stateParams.templateId || undefined,
    };
    return passedParams
}

export {ChangeCreateState}