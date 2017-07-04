import {AlertModal} from "./alert/alert.modal";
import {ConfirmModal} from "./confirm/confirm.modal";
import {TextModal} from "./text/text.modal";

/**
 * Подключение стандартных модальных диалоговых окон
 */
DialogsConfig.$inject = ["ModalActionProvider"];
export function DialogsConfig(ModalActionProvider){
    ModalActionProvider
        .modal(TextModal)
        .modal(AlertModal)
        .modal(ConfirmModal)
}