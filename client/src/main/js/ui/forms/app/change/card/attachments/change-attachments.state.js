import template from "../../../entity-pages/card/attachments/entity-attachments.html"
import {controller} from "../../../entity-pages/card/attachments/entity-attachments.ctrl"
import {SDResolver} from "../../../sd.resolver";
import {ChangeGetterResolver} from "../change.resolver";

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
        SD: SDResolver,
        getEntity: ChangeGetterResolver
    }
};

export {ChangeCardAttachmentsState}