import template from "./sd-status-bar.html"
import {controller} from "./sd-status-bar.ctrl"

/**
 * Компонент для отображения статуса сущности
 * target - сущность ServiceDesk
 * statusList - список возможных статусов.
 * ToDo вшить автоподгрузку statusList внутри компонента
 */
const SDStatusBarComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        statusList: "<"
    }
};

export {SDStatusBarComponent}