import {QDecorator} from "../../utils/Q/QDecorator"
import {servicedeskAPI} from "../api/servicedesk-api"
import {servicedeskUI} from "../ui/servicedesk-ui"


console.log("Init servicedesk module");

/**
 * Основной модуль.
 * Необходим для соединения приложения в одно целое.
 */
export const servicedesk = angular.module("servicedesk",[servicedeskAPI,servicedeskUI])
    .decorator("$q",QDecorator)
    .name
;