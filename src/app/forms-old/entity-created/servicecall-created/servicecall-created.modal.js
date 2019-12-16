import template from "./servicecall-created.html";
import {controller} from "./servicecall-created.ctrl";

/**
 * Описание:
 * Окно-оповщение о создании новой сущности.
 *
 * Параметры:
 * serviceCall - заявка
 *
 * Возвращает:
 * boolean - true = открыть, false - к списку
 */
export const ServiceCallCreatedModal = {
    name: "serviceCallCreated",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};