import {ChangePasswordModal} from "./changePassword/changePassword.modal"
import {ChangePasswordModal as test} from "./test/changePassword.modal"

ModalConfig.$inject = ["ModalActionProvider"];
export function ModalConfig(ModalActionProvider){
    ModalActionProvider
        .modal(ChangePasswordModal)
        .modal(test)
}