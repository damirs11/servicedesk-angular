import {servicedeskAPI} from "../api/servicedesk-api"
import {StateConfig} from "./state/StateConfig"
import {ModalConfig} from "./modal/ModalConfig"
import {uiDialogs} from "../uiDialogs/dialogs";
import {uiRouter, translate, uiBootstrap, uiGrid, ngSanitize, utilsEntity} from "../../utils/web-libraries"
import {TranslateConfig} from "./translate/translate.config"
import {default as ModalAction} from "../ModalAction/ModalAction"


/**
 * Модуль, отвечающий за визуальную часть
 */
export const servicedeskUI = angular.module("servicedesk-ui",[servicedeskAPI, ModalAction, uiRouter, translate, uiBootstrap, ngSanitize])
    .config(TranslateConfig)
    .config(StateConfig)
    .config(ModalConfig)
    .name
;