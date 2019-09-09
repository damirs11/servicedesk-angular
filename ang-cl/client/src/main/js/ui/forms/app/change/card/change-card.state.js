/**
 * Абстрактный стейт отвечающий за работу с конкретным изменением.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./change-card.ctrl"
import template from "./change-card.html"

let ChangeCardState = {
    name: "app.change.card",
    url: "/{entityId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        entity: EntityResolver
    }
};

/**
 * Загружает полностью заполненную сущность с сервера, включая информацию о правах доступа
 * @type {string[]}
 */
EntityResolver.$inject = ["SD", "$stateParams"];
async function EntityResolver(SD, $stateParams) {
    const entity = await new SD.Change($stateParams.entityId).load();
    await entity.updateAccessRules();
    return entity;
}

export {ChangeCardState};