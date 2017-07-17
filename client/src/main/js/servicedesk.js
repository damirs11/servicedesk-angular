import {QDecorator} from "./qdecorator"
import {servicedeskAPI} from "./api/servicedesk-api"
import {servicedeskUI} from "./ui/servicedesk-ui"
/**
 * Основной модуль.
 */
export const servicedesk = angular.module("servicedesk", [servicedeskAPI, servicedeskUI])
    .decorator("$q", QDecorator)
    .name;