/**
 * Абстрактный стейт отвечающий за работу с конкретной заявкой.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./servicecall-card.ctrl";
import template from "./servicecall-card.html";

const ServiceCallCardState = {
    name: "app.servicecall.card",
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
    const entity = await new SD.ServiceCall($stateParams.entityId).load();
    await entity.updateAccessRules();
    return entity;
}

export {ServiceCallCardState};