import template from "./change-approval.html"
import {controller} from "./change-approval.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ChangeCardApprovalState = {
    name: "app.change.card.approval",
    url: "/approval",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver
    }
};

export {ChangeCardApprovalState}