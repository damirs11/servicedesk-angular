import template from "./sd-entity-card-tabs.html"
import {controller} from "./sd-entity-card-tabs.ctrl"

/**
 * Компонент для отображения вкладок в карточке сущности
 * tabs[] - массив вкладок
 * tabs[].name - название вкладки
 * tabs[].sref - путь к стейту
 * tabs[].disabled - состояние вкладки
 */
const SDEntityCardTabsComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        tabs: "<",
    }
};

export {SDEntityCardTabsComponent}