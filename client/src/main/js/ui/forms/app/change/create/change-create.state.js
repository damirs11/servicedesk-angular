import template from "./change-create.html"
import {controller} from "./change-create.ctrl"
import {SDResolver} from "../../sd.resolver";
import {NewChangeResolver} from "./new-change-resolver";

const ChangeCreateState = {
    name: "app.change.create",
    url: "/create",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        change: NewChangeResolver
    }
};

export {ChangeCreateState}