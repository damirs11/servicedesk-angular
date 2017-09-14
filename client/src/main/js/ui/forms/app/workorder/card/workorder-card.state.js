/**
 * Абстрактный стейт отвечающий за работу с конкретным нарядом.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./workorder-card.ctrl"
import template from "./workorder-card.html"
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
        workorderId: WorkorderIdResolver
    }
};

export {WorkorderCardState};