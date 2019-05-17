/**
 * Абстрактный стейт отвечающий за работу с конкретной заявкой.
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./servicecall-card.ctrl";
import template from "./servicecall-card.html";

const ServiceCallCardState = {
    name: "app.servicecall.card",
    url: "/{serviceCallId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        entity: entityResolver
    }
};

/**
 * Загружает полностью заполненную сущность с сервера, включая информацию о правах доступа
 * @type {string[]}
 */
entityResolver.$inject = ["SD", "$stateParams"];
async function entityResolver(SD, $stateParams) {
    const entity = await new SD.ServiceCall($stateParams.serviceCallId).load();
    await entity.updateAccessRules();
    return entity;
}

export {ServiceCallCardState};