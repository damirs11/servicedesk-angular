import {SDFactory} from "./SD/SD.factory"

/**
 * Модуль, предоставляющий API бэкэнда
 */
let servicedeskAPI = angular.module("servicedesk-api",[])
    .factory('SD',SDFactory)
    .name
;

export {servicedeskAPI}