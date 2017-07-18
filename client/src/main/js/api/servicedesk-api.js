/**
 * Модуль, предоставляющий API бэкэнда
 */
import {SDFactory} from "./sd.factory";
import {ConnectorProvider} from "./connector/connector-provider";
import {SessionFactory} from "./session.factory";

const servicedeskAPI = angular.module("servicedesk-api", [])
    .provider("$connector", ConnectorProvider)
    .factory('SD', SDFactory)
    .factory('Session', SessionFactory)
    .name
;

export {servicedeskAPI};