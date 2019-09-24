/**
 * Подключение стандартных модальных диалоговых окон
 */
import {AlertModal} from "./alert/alert.modal";
import {ConfirmModal} from "./confirm/confirm.modal";
import {TextModal} from "./text/text.modal";
import {ImagePopupModal} from "./image-popup/image-popup.modal";

DialogsConfig.$inject = ["ModalActionProvider"];
export function DialogsConfig(ModalActionProvider){
    ModalActionProvider
        .modal(TextModal)
        .modal(AlertModal)
        .modal(ConfirmModal)
        .modal(ImagePopupModal)
}