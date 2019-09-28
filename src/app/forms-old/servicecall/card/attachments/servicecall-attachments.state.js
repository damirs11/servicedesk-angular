import template from "../../../entity-pages/card/attachments/entity-attachments.html";
import {controller} from "../../../entity-pages/card/attachments/entity-attachments.ctrl";
import {SDResolver} from "../../../sd.resolver";

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
        getEntity: ['entity', (entity) => () => entity]
    }
};

export {ServiceCallCardAttachmentsState};