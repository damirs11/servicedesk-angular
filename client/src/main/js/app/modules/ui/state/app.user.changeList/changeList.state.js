import {ChangeListController as controller} from "./changeList.ctrl.js"
import template from "./changeList.tpl.html"

let ChangeListState = {
    name:"app.user.changeList",
    url:"/change",
    controller:controller,
    template:template,
    controllerAs:"ctrl"
};

export {ChangeListState}