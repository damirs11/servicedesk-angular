import {QDecorator} from "./qdecorator"
import {servicedeskAPI} from "./api/servicedesk-api"
import {servicedeskUI} from "./ui/servicedesk-ui"
import angular from "angular";

/**
 * Основной модуль.
 */

if ('GULP_REPLACE:DEBUG') console.warn("Running in debug mode.");

export const servicedesk = angular.module("servicedesk", [servicedeskAPI, servicedeskUI])
    .decorator("$q", QDecorator)
    .name;