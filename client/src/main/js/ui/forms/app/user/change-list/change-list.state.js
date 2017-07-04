import {ChangeListController as controller} from "./change-list.ctrl.js";
import template from "./change-list.tpl.html";

let ChangeListState = {
    name:"app.user.change-list",
    url:"/change",
    controller:controller,
    template:template,
    controllerAs:"ctrl"
};

export {ChangeListState};