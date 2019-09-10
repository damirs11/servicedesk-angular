/**
 * Модуль, предоставляющий API бэкэнда
 */
import {SDFactory} from "./sd.factory";
import {ConnectorProvider} from "./connector/connector-provider";
import {SessionFactory} from "./session.factory";
import {SDAccessProvider} from "./sd-access/sd-access.provider";

const servicedeskAPI = angular.module("servicedesk-api", ["ngFileUpload"])
    .provider("$connector", ConnectorProvider)
    .provider("$sdAccess", SDAccessProvider)
    .factory('SD', SDFactory)
    .factory('Session', SessionFactory)
    .name
;

export {servicedeskAPI};