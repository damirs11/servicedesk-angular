import template from "./sd-approval.html"
import {controller} from "./sd-approval.ctrl"

/**
 * Компонент для отображения и редактирования основной информации согласования
 * target - сущность ServiceDesk
 * SD - объект SD, необходимо пробрасывать, чтобы кэш был одним.
 */
const SDApprovalComponent = {
    template: template,
    controller: controller,
    controllerAs: "ctrl",
    bindings: {
        target: "=",
        sd: "<"
    }
};

export {SDApprovalComponent}