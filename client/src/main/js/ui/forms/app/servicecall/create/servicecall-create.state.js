import template from "./servicecall-create.html";
import {controller} from "./servicecall-create.ctrl";
import {SDResolver} from "../../sd.resolver";
import {NewServiceCallResolver} from "./new-servicecall-resolver";
import {PassedParamsResolver} from "./passed-arguments-resolver";

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
        serviceCall: NewServiceCallResolver,
        passedParams: PassedParamsResolver
    }
};

export {ServiceCallCreateState};