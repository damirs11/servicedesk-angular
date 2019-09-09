import template from "./sd-avatar.html"
import {controller} from "./sd-avatar.ctrl"

/**
 * Компонент для отображения аватарки
 * url - ссылка на картинку
 * link - ссылка, куда будет перебрасывать нажатие на картинку
 * type - [circle,square,default] - форма автарки
 * center - выравнять по центру
 * size - размер.
 */
const SDAvatarComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        url: "@",
        link: "@",
        type: "@",
        center: "<",
        size: "<",
    }
};

export {SDAvatarComponent}