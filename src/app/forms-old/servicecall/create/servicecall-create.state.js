import template from "./servicecall-create.html";
import {controller} from "./servicecall-create.ctrl";
import {SDResolver} from "../../sd.resolver";

const ServiceCallCreateState = {
    name: "app.servicecall.create",
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
        entity: NewEntityResolver,
        passedParams: PassedParamsResolver
    }
};

NewEntityResolver.$inject = ["SD"];
function NewEntityResolver(SD) {
    return new SD.ServiceCall().$update({
        assignment: new SD.EntityAssignment(),
    });
}

PassedParamsResolver.$inject = ["$stateParams"];
function PassedParamsResolver($stateParams){
    return {
        templateId: $stateParams.templateId || undefined,
    };
}

export {ServiceCallCreateState};