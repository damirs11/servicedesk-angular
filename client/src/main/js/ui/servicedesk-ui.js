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
    uiGridSaveState,
    ngFileUpload,
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
import {SDNumberComponent} from "./components/fields/sd-number/sd-number.component";
import {SDAttachmentsComponent} from "./components/widget/sd-attachments/sd-attachments.component";
import {SDAttachmentItemComponent} from "./components/widget/sd-attachments/sd-attachment-item/sd-attachment-item.component";
import {SDAttachBoxComponent} from "./components/widget/sd-attachments/sd-attach-box/sd-attach-box.component";
import {SDUploadingFileComponent} from "./components/widget/sd-attachments/sd-uploading-file/sd-uploading-file.component";
import {SDLoadingDirective} from "./components/directives/sd-loading/sd-loading.directive";
import {AutoHeightDirective} from "./components/directives/auto-height.directive";
import {PageLockFactory} from "./components/page-lock.factory";
import {SDEntityErrorDirective} from "./components/widget/sd-entity-error/sd-entity-error.directive";
import {SDEntityCardTabsComponent} from "./components/widget/sd-entity-card-tabs/sd-entity-card-tabs.component";
import {SDIconFilter} from "./components/filters/sd-icon.filter";
import {SDPageLinkFilter} from "./components/filters/sd-page-link.filter";
import {SDVoteComponent} from "./components/widget/sd-vote/sd-vote.component";

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
            uiGridSaveState,
            ngFileUpload])

    .config(TranslateConfig)
    .config(FormConfig)
    .config(DialogsConfig)
    .config(ConnectorConfig)
    .config(uiSelectConfig)
    .config(UiGridConfig)

    .filter("sdIcon",SDIconFilter)
    .filter("sdPageLink",SDPageLinkFilter)

    .factory("$grid",$gridFactory)
    .factory("SDValidator",SDValidatorFactory)
    .factory("$pageLock",PageLockFactory)

    .directive("equalsTo", EqualsTo)
    .directive("differentFrom", DifferentFrom)
    .directive("sdFocus",SDFocusDirective)
    .directive("sdOnInteractOut",SDOnInteractOutDirective)
    .directive("sdLoading",SDLoadingDirective)
    .directive("autoHeight",AutoHeightDirective)
    .directive("sdEntityError",SDEntityErrorDirective)

    /** Компоненты для редактируемых полей */
    .component("sdText", SDTextComponent)
    .component("sdTextarea", SDTextareaComponent)
    .component("sdDropdown", SDDropdownComponent)
    .component("sdDatetime", SDDateTimeComponent)
    .component("sdNumber", SDNumberComponent)
    /** Прочие */
    .component("sdEntityCardTabs",SDEntityCardTabsComponent)
    .component("sdStatusBar",SDStatusBarComponent)
    .component("sdAvatar",SDAvatarComponent)
    .component("sdVote",SDVoteComponent)
    /** Компоненты для чата */
    .component("chatLine",ChatLineComponent)
    .component("chatInfoBlock",ChatInfoBlockComponent)
    .component("chatInput",ChatInputComponent)
    .component("sdEntityChat",SDEntityChatComponent)
    /** Вложения */
    .component("sdAttachments",SDAttachmentsComponent)
    .component("sdAttachmentItem",SDAttachmentItemComponent)
    .component("sdAttachBox",SDAttachBoxComponent)
    .component("sdUploadingFile",SDUploadingFileComponent)

    .controller("IndexController",IndexController)
    .name;