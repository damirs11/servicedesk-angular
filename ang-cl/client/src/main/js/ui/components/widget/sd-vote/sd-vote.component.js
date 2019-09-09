import template from "./sd-vote.html"
import {controller} from "./sd-vote.ctrl"

/**
 * Компонент для отображения аватарки
 * vote {SD.ApproverVote} - голос персоны, который будет изменяться
 * onVote {func} - выполнится при (после) голосовании
 */
const SDVoteComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        vote: "<",
        onVote: "&"
    }
};

export {SDVoteComponent}