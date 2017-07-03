import {ChangeListController as controller} from "change-list.ctrl.js";
import template from "src/main/js/ui/forms/app/user/change-list/change-list.tpl.html";

let ChangeListState = {
    name:"app.user.change-list",
    url:"/change",
    controller:controller,
    template:template,
    controllerAs:"ctrl"
};

export {ChangeListState};