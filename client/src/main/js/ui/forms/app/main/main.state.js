import {MainController as controller} from "main.ctrl.js";
import template from "main.tpl.html";

let MainState = {
    name:"app.main",
    url:"",
    controller:controller,
    template:template,
    controllerAs:"ctrl",
    data: {
        needAuthorize: false
    }
};

export {MainState};