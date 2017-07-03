import {SDFactory} from "entity/sd-factory";
import {ConnectorProvider} from "connector/connector-provider";
import {HttpInterceptorsFactory, HttpInterceptorsConfig} from "connector/http-interceptors";

/**
 * Модуль, предоставляющий API бэкэнда
 */
const servicedeskAPI = angular.module("servicedesk-api", [])
    .provider("$connector", ConnectorProvider)
    .factory('SD', SDFactory)
    .factory('httpInterceptors', HttpInterceptorsFactory)
    .config(HttpInterceptorsConfig)
    .name
;

export {servicedeskAPI};