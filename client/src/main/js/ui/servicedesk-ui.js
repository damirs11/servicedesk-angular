import {servicedeskAPI} from "api/servicedesk-api";
import {StateConfig} from "forms/StateConfig";
import {DialogsConfig} from "components/dialogs/dialogs.config";
import {uiRouter, translate, uiBootstrap, uiGrid, ngSanitize, utilsEntity, ngMessages} from "common/web-libraries.const";
import {TranslateConfig} from "components/translate/translate.config";
import {default as ModalAction} from "components/modal-action/modal-action";
import {EqualsTo} from "components/validators/equals-to";
import {DifferentFrom} from "components/validators/different-from";
import {AddMessages} from "components/templates/Templates";

/**
 * Модуль, отвечающий за визуальную часть
 * Подключение всего, что используется в UI
 */
export const servicedeskUI = angular.module("servicedesk-ui", [servicedeskAPI, ModalAction, uiRouter, ngMessages, translate, uiBootstrap, ngSanitize])
    .config(TranslateConfig)
    .config(StateConfig)
    .config(DialogsConfig)
    .directive("equalsTo", EqualsTo)
    .directive("differentFrom", DifferentFrom)
    .run(AddMessages)
    .name;