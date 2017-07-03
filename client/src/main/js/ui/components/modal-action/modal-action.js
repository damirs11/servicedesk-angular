import {ngAnimate} from "../../common/web-libraries.const";
import ModalActionProvider from 'modal-action.provider';

export default angular.module("modal-action", [ngAnimate])
    .provider("ModalAction", ModalActionProvider)
    .name;