import {ChangePasswordModal} from "./changePassword/changePassword.modal"
import {GetMessageModal} from "./input/getMessage/getMessage.modal"
import {AlertModal} from "./input/alert/alert.modal"
import {ConfirmModal} from "./input/confirm/confirm.modal"

ModalConfig.$inject = ["ModalActionProvider"];
export function ModalConfig(ModalActionProvider){
    ModalActionProvider
        .modal(ChangePasswordModal)
        .modal(GetMessageModal)
        .modal(AlertModal)
        .modal(ConfirmModal)
}