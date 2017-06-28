import {SDFactory} from "./SD/SD.factory"
import {ConnectorProvider} from "./connector/connector.provider";

/**
 * Модуль, предоставляющий API бэкэнда
 */
export const servicedeskAPI = angular.module("servicedesk-api",[])
    .provider("$connector",ConnectorProvider)
    .factory('SD',SDFactory)
    .name
;