import template from "./workorder-create.html"
import {controller} from "./workorder-create.ctrl"
import {SDResolver} from "../../sd.resolver";
import {NewWorkorderResolver} from "./new-workorder-resolver";

const WorkorderCreateState = {
    name: "app.workorder.create",
    url: "/create?changeId",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        workorder: NewWorkorderResolver
    }
};

export {WorkorderCreateState}