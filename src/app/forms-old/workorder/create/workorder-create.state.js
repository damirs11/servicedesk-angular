import template from "./workorder-create.html"
import {controller} from "./workorder-create.ctrl"
import {SDResolver} from "../../sd.resolver";

const WorkorderCreateState = {
    name: "app.workorder.create",
    url: "/create?changeId?problemId?templateId",
    controller: controller,
    template: template,
    reloadOnSearch: false,
    controllerAs: "ctrl",
    abstract: true,
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        workorder: NewWorkorderResolver,
        passedParams: PassedParamsResolver
    }
};

NewWorkorderResolver.$inject = ["SD"];
function NewWorkorderResolver(SD) {
    const workorder = new SD.Workorder();
    workorder.$updateRaw({assignment: new SD.EntityAssignment()});
    return workorder;
}

PassedParamsResolver.$inject = ["$stateParams"];
function PassedParamsResolver($stateParams){
    const passedParams = {
        changeId: $stateParams.changeId || undefined,
        problemId: $stateParams.problemId || undefined,
        templateId: $stateParams.templateId || undefined,
    };
    return passedParams
}

export {WorkorderCreateState}