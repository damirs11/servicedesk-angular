import template from "./change.html"
import {controller} from "./change.ctrl"
import {ChangeIdResolver} from "./change-id.resolver"
import {SDResolver} from "../sd.resolver";

const ChangeState = {
    name: "app.change",
    url: "/change/{changeId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    resolve: {
        changeId: ChangeIdResolver,
        SD: SDResolver
    },
    data: {
        needAuthorize: true
    }
};

export {ChangeState}