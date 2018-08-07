/**
 * Абстрактный стейт отвечающий за работу с конкретным изменением.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./change-card.ctrl"
import template from "./change-card.html"
import {ChangeIdResolver} from "./change.resolver";

let ChangeCardState = {
    name: "app.change.card",
    url: "/{changeId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        changeId: ChangeIdResolver
    }
};

export {ChangeCardState};