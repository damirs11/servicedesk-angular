import {ngAnimate} from "../../utils/web-libraries"
import ModalActionProvider from './components/ModalActionProvider'

export default angular.module("modal-action", [ngAnimate])
    .provider("ModalAction", ModalActionProvider)
    .name;
