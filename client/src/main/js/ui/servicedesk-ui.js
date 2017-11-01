/**
 * Модуль, отвечающий за визуальную часть
 * Подключение всего, что используется в UI
 */
import {servicedeskAPI} from "../api/servicedesk-api";
import {DialogsConfig} from "./components/dialogs/dialogs.config";
import {
    uiRouter,
    translate,
    uiBootstrap,
    uiSelect,
    uiGrid,
    uiGridAutoResize,
    uiGridSelection,
    uiGridPagination,
    uiGridExporter,
    uiGridResizeColumns,
    ngSanitize,
    ngMessages,
    uiBootstrapDatetimePicker,
    angularMoment,
    uiGridSaveState
} from "../common/web-libraries.const";
import {TranslateConfig} from "./components/translate/translate.config";
import {default as ModalAction} from "./components/modal-action/modal-action";
import {EqualsTo} from "./components/validators/equals-to";
import {DifferentFrom} from "./components/validators/different-from";
import {FormConfig} from "./forms/form.config";
import {ConnectorConfig} from "./components/connector.config";
import {IndexController} from "./index.ctrl";
import {SDFocusDirective} from "./components/directives/sd-focus.directive";
import {SDOnInteractOutDirective} from "./components/directives/sd-on-interact-out";
import {uiSelectConfig} from "./components/uiSelect.config";
import {SDTextComponent} from "./components/fields/sd-text/sd-text.component";
import {SDDropdownComponent} from "./components/fields/sd-dropdown/sd-dropdown.component";
import {SDDateTimeComponent} from "./components/fields/sd-datetime/sd-datetime.component";
import {SDTextareaComponent} from "./components/fields/sd-textarea/sd-textarea.component";
import {UiGridConfig} from "./components/widget/grid/ui-grid.config.js";
import {SDStatusBarComponent} from "./components/widget/status-bar/sd-status-bar.component";
import {SDEntityChatComponent} from "./components/widget/sd-entity-chat/sd-entity-chat.component";
import {SDValidatorFactory} from "./components/sd-validator/sd-validator.factory";
import {$gridFactory} from "./components/widget/grid/grid.factory";
import {SDAvatarComponent} from "./components/widget/sd-avatar/sd-avatar.component";
import {ChatLineComponent} from "./components/widget/sd-entity-chat/chat-line/chat-line.component";
import {ChatInfoBlockComponent} from "./components/widget/sd-entity-chat/chat-line/chat-info-block/chat-info-block.component";
import {ChatInputComponent} from "./components/widget/sd-entity-chat/chat-input/chat-input.component";
import {SDApprovalComponent} from "./components/widget/sd-approval/sd-approval.component";
import {SDNumberComponent} from "./components/fields/sd-number/sd-number.component";
import {SDAttachmentsComponent} from "./components/widget/sd-attachments/sd-attachments.component";
import {SDAttachmentItemComponent} from "./components/widget/sd-attachments/sd-attachment-item/sd-attachment-item.component";
import {SDAttachmentUploaderComponent} from "./components/widget/sd-attachments/sd-attachment-uploader/sd-attachment-uploader.component";

export const servicedeskUI = angular.module("servicedesk-ui",
        [
        servicedeskAPI,
            ModalAction,
            uiRouter,
            ngMessages,
            translate,
            uiBootstrap,
            ngSanitize,
            uiGrid,
            uiGridAutoResize,
            uiGridSelection,
            uiGridPagination,
            uiGridExporter,
            uiGridResizeColumns,
            uiSelect,
            uiBootstrapDatetimePicker,
            angularMoment,
            uiGridSaveState])

    .config(TranslateConfig)
    .config(FormConfig)
    .config(DialogsConfig)
    .config(ConnectorConfig)
    .config(uiSelectConfig)
    .config(UiGridConfig)

    .factory("$grid",$gridFactory)
    .factory("SDValidator",SDValidatorFactory)

    .directive("equalsTo", EqualsTo)
    .directive("differentFrom", DifferentFrom)
    .directive("sdFocus",SDFocusDirective)
    .directive("sdOnInteractOut",SDOnInteractOutDirective)

    /** Компоненты для редактируемых полей */
    .component("sdText", SDTextComponent)
    .component("sdTextarea", SDTextareaComponent)
    .component("sdDropdown", SDDropdownComponent)
    .component("sdDatetime", SDDateTimeComponent)
    .component("sdNumber", SDNumberComponent)
    /** Прочие */
    .component("sdStatusBar",SDStatusBarComponent)
    .component("sdAvatar",SDAvatarComponent)
    /** Компоненты для чата */
    .component("chatLine",ChatLineComponent)
    .component("chatInfoBlock",ChatInfoBlockComponent)
    .component("chatInput",ChatInputComponent)
    .component("sdEntityChat",SDEntityChatComponent)
    /** Согласование */
    .component("sdApproval",SDApprovalComponent)
    /** Вложения */
    .component("sdAttachments",SDAttachmentsComponent)
    .component("sdAttachmentItem",SDAttachmentItemComponent)
    .component("sdAttachmentUploader",SDAttachmentUploaderComponent)

    .controller("IndexController",IndexController)
    .name;