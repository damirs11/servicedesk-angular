import template from "./connector-error.html"
import {controller} from "./connector-error.ctrl"

/**
 * Описание:
 * Окно показывающее серверную ошибку
 * Параметры:
 * @param error {ConnectorRespError} - ошибка, содержащая ответ сервера
 */
export const ConnectorErrorModal = {
    name: "connectorError",
    template: template,
    controller: controller,
    controllerAs: "ctrl"
};