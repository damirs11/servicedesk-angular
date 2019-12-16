import template from "./entity-changed.html"
import {EntityChangedModalController as controller} from "./entity-changed.ctrl"

/**
 * Описание:
 * окно для смены пароля.
 *
 * Параметры:
 * -
 *
 * Возвращает:
 * boolean - результат изменения пароля
 */
export const EntityChangedModal = {
    name: "entityChanged",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};