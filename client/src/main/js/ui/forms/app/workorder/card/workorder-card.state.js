/**
 * Абстрактный стейт отвечающий за работу с конкретным нарядом.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./change-card.ctrl"
import template from "./change-card.html"
import {ChangeIdResolver} from "./change-id.resolver";
import {WorkorderIdResolver} from "./workorder-id.resolver";

let WorkorderCardState = {
    name: "app.workorder.card",
    url: "/{workorderId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        changeId: WorkorderIdResolver
    }
};

export {WorkorderCardState};