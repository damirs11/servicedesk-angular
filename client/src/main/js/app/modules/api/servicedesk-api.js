import {SDFactory} from "./SD/SD.factory"
import {ConnectorProvider} from "./connector/connector.provider";
import {ErrorInterceptorsFactory} from "./connector/interceptors/errorInterceptors.factory"
import {HttpInterceptorsConfig} from "./connector/interceptors/httpInterceptorsConfig"

/**
 * Модуль, предоставляющий API бэкэнда
 */
export const servicedeskAPI = angular.module("servicedesk-api",[])
    .provider("$connector",ConnectorProvider)
    .factory('SD',SDFactory)
    .factory('errorInterceptors',ErrorInterceptorsFactory)
    .config(HttpInterceptorsConfig)
    .name
;