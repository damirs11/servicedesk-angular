import template from "./change-attachments.html"
import {controller} from "./change-attachments.ctrl"
import {SDResolver} from "../../../sd.resolver";

const ChangeCardAttachmentsState = {
    name: "app.change.card.attachments",
    url: "/attachments",
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

export {ChangeCardAttachmentsState}