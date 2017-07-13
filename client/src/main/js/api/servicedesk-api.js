import {SDFactory} from "./sd.factory";
import {ConnectorProvider} from "./connector/connector-provider";

/**
 * Модуль, предоставляющий API бэкэнда
 */
const servicedeskAPI = angular.module("servicedesk-api", [])
    .provider("$connector", ConnectorProvider)
    .factory('SD', SDFactory)
    .name
;

export {servicedeskAPI};