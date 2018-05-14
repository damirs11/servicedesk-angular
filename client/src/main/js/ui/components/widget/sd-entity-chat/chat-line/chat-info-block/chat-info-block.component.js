import template from "./chat-info-block.html"

/**
 * Блок, отображающийся справа или слева от сообщения
 * name - имя персоны
 * avatar - ссылка на аватарку персоны
 */
const ChatInfoBlockComponent = {
    template: template,
    controllerAs: "ctrl",
    bindings: {
        person: "<"
    }
};

export {ChatInfoBlockComponent}