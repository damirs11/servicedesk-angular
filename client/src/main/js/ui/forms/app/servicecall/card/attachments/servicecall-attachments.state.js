import template from "../../../entity-pages/card/attachments/entity-attachments.html";
import {controller} from "../../../entity-pages/card/attachments/entity-attachments.ctrl";
import {SDResolver} from "../../../sd.resolver";
import {ServiceCallGetterResolver} from "../servicecall.resolver";

const ServiceCallCardAttachmentsState = {
    name: "app.servicecall.card.attachments",
    url: "/attachments",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        SD: SDResolver,
        getEntity: ServiceCallGetterResolver
    }
};

export {ServiceCallCardAttachmentsState};