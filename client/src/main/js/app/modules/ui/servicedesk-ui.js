import {servicedeskAPI} from "../api/servicedesk-api"
import {StateConfig} from "./state/StateConfig"
import {uiDialogs} from "../uiDialogs/dialogs";
import {uiRouter, translate, uiBootstrap, uiGrid, ngSanitize, utilsEntity} from "./utils/angularLibraries"
import {name as TranslateLoaderName, TranslateLoaderFactory} from "./translate/translateLoader.factory"
import {TranslateConfig} from "./translate/translate.config"

let servicedeskUI = angular.module("servicedesk-ui",[servicedeskAPI, uiRouter, translate, uiDialogs, uiBootstrap, ngSanitize])
    .factory(TranslateLoaderName,TranslateLoaderFactory)
    .config(TranslateConfig)
    .config(StateConfig)
    .name
;


export {servicedeskUI}