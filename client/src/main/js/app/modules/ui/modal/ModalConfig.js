import {ChangePasswordModal} from "./changePassword/changePassword.modal"

ModalConfig.$inject = ["ModalActionProvider"];
export function ModalConfig(ModalActionProvider){
    ModalActionProvider
        .modal(ChangePasswordModal)
}