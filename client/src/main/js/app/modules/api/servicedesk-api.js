import {SDFactory} from "./SD/SD.factory"

let servicedeskAPI = angular.module("servicedesk-api",[])
    .factory('SD',SDFactory)
    .name
;

export {servicedeskAPI}