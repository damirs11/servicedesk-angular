import template from "./workorder-create.html"
import {controller} from "./workorder-create.ctrl"
import {SDResolver} from "../../sd.resolver";
import {NewWorkorderResolver} from "./new-workorder-resolver";
import {PassedParamsResolver} from "./passed-arguments-resolver";

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

export {WorkorderCreateState}