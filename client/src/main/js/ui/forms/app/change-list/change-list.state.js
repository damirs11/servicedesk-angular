import {ChangeListController as controller} from "./change-list.ctrl.js";
import template from "./change-list.tpl.html";

let ChangeListState = {
    name:"app.change-list",
    url:"/change",
    controller:controller,
    template:template,
    controllerAs:"ctrl",
    data: {
        needAuthorize: true
    }
};

export {ChangeListState};