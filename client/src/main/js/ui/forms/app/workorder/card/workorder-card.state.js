/**
 * Абстрактный стейт отвечающий за работу с конкретным нарядом.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./workorder-card.ctrl"
import template from "./workorder-card.html"

let WorkorderCardState = {
    name: "app.workorder.card",
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
    const entity = await new SD.Workorder($stateParams.entityId).load();
    await entity.updateAccessRules();
    return entity;
}

export {WorkorderCardState};