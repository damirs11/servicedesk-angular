/**
 * Модуль, отвечающий за визуальную часть
 * Подключение всего, что используется в UI
 */
import {servicedeskAPI} from "../api/servicedesk-api";
import {DialogsConfig} from "./components/dialogs/dialogs.config";
import {uiRouter, translate, uiBootstrap, uiGrid, uiGridSelection, uiGridPagination, ngSanitize, utilsEntity, ngMessages} from "../common/web-libraries.const";
import {TranslateConfig} from "./components/translate/translate.config";
import {default as ModalAction} from "./components/modal-action/modal-action";
import {EqualsTo} from "./components/validators/equals-to";
import {DifferentFrom} from "./components/validators/different-from";
import {FormConfig} from "./forms/form.config";
import {IndexController} from "./index.ctrl";

export const servicedeskUI = angular.module("servicedesk-ui", [servicedeskAPI, ModalAction, uiRouter, ngMessages, translate,
        uiBootstrap, ngSanitize, uiGrid, uiGridSelection, uiGridPagination])
    .config(TranslateConfig)
    .config(FormConfig)
    .config(DialogsConfig)
    .directive("equalsTo", EqualsTo)
    .directive("differentFrom", DifferentFrom)
    .controller("IndexController",IndexController)
    .name;