import template from "./problem-created.html"
import {controller} from "./problem-created.ctrl"

/**
 * Описание:
 * Окно-оповщение о создании новой сущности.
 *
 * Параметры:
 * problem - проблема
 *
 * Возвращает:
 * boolean - true = открыть, false - к списку
 */
export const ProblemCreatedModal = {
    name: "problemCreated",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};