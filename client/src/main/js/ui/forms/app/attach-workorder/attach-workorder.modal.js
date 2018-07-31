import template from "./attach-workorder.html"
import {controller} from "./attach-workorder.ctrl"

/**
 * Описание: прикрепляет наряд к сущности
 * @param entity {SD.Entity} - сущность, к которой прикрепится наряд
 * @param workorderField {String} - поле в сущности SD.Workorder, куда занесется entity
 */
export const AttachWorkorderModal = {
    name: "attachWorkorder",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};