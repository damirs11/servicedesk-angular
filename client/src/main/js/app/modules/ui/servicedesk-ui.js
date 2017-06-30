import {servicedeskAPI} from "../api/servicedesk-api"
import {StateConfig} from "./state/StateConfig"
import {ModalConfig} from "./modal/ModalConfig"
import {uiRouter, translate, uiBootstrap, uiGrid, ngSanitize, utilsEntity, ngMessages} from "../../utils/web-libraries"
import {TranslateConfig} from "./translate/translate.config"
import {default as ModalAction} from "../ModalAction/ModalAction"
import {EqualsTo} from "./validators/equalsTo"
import {DifferentFrom} from "./validators/differentFrom"
import {AddMessages} from "./messages/AddMessages"


/**
 * Модуль, отвечающий за визуальную часть
 */
export const servicedeskUI = angular.module("servicedesk-ui",[servicedeskAPI, ModalAction, uiRouter, ngMessages, translate, uiBootstrap, ngSanitize])
    .config(TranslateConfig)
    .config(StateConfig)
    .config(ModalConfig)
    .directive("equalsTo",EqualsTo)
    .directive("differentFrom",DifferentFrom)
    .run(AddMessages)
    .name
;