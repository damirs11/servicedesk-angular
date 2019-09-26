import template from "./change-created.html"
import {controller} from "./change-created.ctrl"

/**
 * Описание:
 * Окно-оповщение о создании новой сущности.
 *
 * Параметры:
 * change - изменение
 *
 * Возвращает:
 * boolean - true = открыть, false - к списку
 */
export const ChangeCreatedModal = {
    name: "changeCreated",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};