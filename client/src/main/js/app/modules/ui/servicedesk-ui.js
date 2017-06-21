import {servicedeskAPI} from "../api/servicedesk-api"
import {StateConfig} from "./state/StateConfig"
import {uiDialogs} from "../uiDialogs/dialogs";
import {uiRouter, translate, uiBootstrap, uiGrid, uiTest, utilsEntity} from "./utils/angularLibraries"

let servicedeskUI = angular.module("servicedesk-ui",[uiRouter, translate, uiDialogs, uiBootstrap])
    .config(StateConfig)
    .name
;

export {servicedeskUI}