/**
 * Абстрактный стейт отвечающий за работу с конкретным изменением.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./change-card.ctrl"
import {ChangeIdResolver} from "./change-id.resolver";

let ChangeCardState = {
    name: "app.change.card",
    url: "/{changeId:int}",
    controller: controller,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        changeId: ChangeIdResolver
    }
};

export {ChangeCardState};