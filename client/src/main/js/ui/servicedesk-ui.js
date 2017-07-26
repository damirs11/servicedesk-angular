/**
 * Модуль, отвечающий за визуальную часть
 * Подключение всего, что используется в UI
 */
import {servicedeskAPI} from "../api/servicedesk-api";
import {DialogsConfig} from "./components/dialogs/dialogs.config";
import {uiRouter, translate, uiBootstrap, uiSelect, uiGrid, uiGridSelection, uiGridPagination, ngSanitize, ngMessages, uiBootstrapDatetimePicker} from "../common/web-libraries.const";
import {TranslateConfig} from "./components/translate/translate.config";
import {default as ModalAction} from "./components/modal-action/modal-action";
import {EqualsTo} from "./components/validators/equals-to";
import {DifferentFrom} from "./components/validators/different-from";
import {FormConfig} from "./forms/form.config";
import {ConnectorConfig} from "./components/connector.config";
import {IndexController} from "./index.ctrl";
import {SDFocusDirective} from "./components/directives/sd-focus.directive";
import {SDOnInteractOutDirective} from "./components/directives/sd-on-interact-out";
import {SDTextComponent} from "./components/fields/sd-text/sd-text.component";
import {SDSelectComponent} from "./components/fields/sd-select/sd-select.component";
import {SDSelectSearchComponent} from "./components/fields/sd-select-search/sd-select-search.component";
import {uiSelectConfig} from "./components/uiSelect.config";
import {SDDropdownComponent} from "./components/fields/sd-dropdown/sd-dropdown.component";
import {SDDateTimeComponent} from "./components/fields/sd-datetime/sd-datetime.component";

export const servicedeskUI = angular.module("servicedesk-ui", [servicedeskAPI, ModalAction, uiRouter, ngMessages, translate,
        uiBootstrap, ngSanitize, uiGrid, uiGridSelection, uiGridPagination, uiSelect, uiBootstrapDatetimePicker])
    .config(TranslateConfig)
    .config(FormConfig)
    .config(DialogsConfig)
    .config(ConnectorConfig)
    .config(uiSelectConfig)
    .directive("equalsTo", EqualsTo)
    .directive("differentFrom", DifferentFrom)
    .directive("sdFocus",SDFocusDirective)
    .directive("sdOnInteractOut",SDOnInteractOutDirective)
    .component("sdText", SDTextComponent)
    .component("sdSelect", SDSelectComponent)
    .component("sdDropdown", SDDropdownComponent)
    .component("sdSelectSearch", SDSelectSearchComponent)
    .component("sdDatetime", SDDateTimeComponent)
    .controller("IndexController",IndexController)
    .name;