import {SDFactory} from "./SD/SD.factory"
import {ConnectorProvider} from "./connector/connector.provider";

/**
 * Модуль, предоставляющий API бэкэнда
 */
let servicedeskAPI = angular.module("servicedesk-api",[])
    .provider("$connector",ConnectorProvider)
    .factory('SD',SDFactory)
    .name
;

export {servicedeskAPI}