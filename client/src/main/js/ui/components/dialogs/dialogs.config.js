import {AlertModal} from "alert/alert.modal";
import {ConfirmModal} from "confirm/confirm.modal";
import {TextModal} from "text/text.modal";

/**
 * Подключение стандартных модальных диалоговых окон
 */
ModalConfig.$inject = ["ModalActionProvider"];
export function ModalConfig(ModalActionProvider){
    ModalActionProvider
        .modal(TextModal)
        .modal(AlertModal)
        .modal(ConfirmModal)
}