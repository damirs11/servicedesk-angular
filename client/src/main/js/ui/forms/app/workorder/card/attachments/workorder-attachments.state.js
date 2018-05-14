import template from "./workorder-attachments.html"
import {controller} from "./workorder-attachments.ctrl"
import {SDResolver} from "../../../sd.resolver";

const WorkorderCardAttachmentsState = {
    name: "app.workorder.card.attachments",
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

export {WorkorderCardAttachmentsState}