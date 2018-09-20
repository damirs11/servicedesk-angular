import template from "./change-create.html"
import {controller} from "./change-create.ctrl"
import {SDResolver} from "../../sd.resolver";
import {NewChangeResolver} from "./new-change-resolver";
import {PassedParamsResolver} from "./passed-arguments-resolver";

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

export {ChangeCreateState}