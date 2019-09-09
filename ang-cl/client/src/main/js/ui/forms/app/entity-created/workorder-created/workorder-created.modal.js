import template from "./workorder-created.html"
import {controller} from "./workorder-created.ctrl"

/**
 * Описание:
 * Окно-оповщение о создании новой сущности.
 *
 * Параметры:
 * workorder - наряд
 *
 * Возвращает:
 * boolean - true = открыть, false - к списку
 */
export const WorkorderCreatedModal = {
    name: "workorderCreated",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};