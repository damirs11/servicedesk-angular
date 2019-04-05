/**
 * Абстрактный стейт отвечающий за работу с конкретной заявкой.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./servicecall-card.ctrl";
import template from "./servicecall-card.html";
import {ServiceCallIdResolver} from "./servicecall.resolver";

let ServiceCallCardState = {
    name: "app.servicecall.card",
    url: "/{serviceCallId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        serviceCallId: ServiceCallIdResolver
    }
};

export {ServiceCallCardState};