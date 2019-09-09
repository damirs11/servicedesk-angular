import template from "./make-vote.html"
import {controller} from "./make-vote.ctrl"

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
export const MakeVoteModal = {
    name: "makeVote",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};