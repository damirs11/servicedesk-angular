import {AppController as controller} from "./app.ctrl.js";
import template from "./app.tpl.html";
import {UserResolver} from "./user.resolver";

let AppState = {
    name:"app",
    url:"",
    controller:controller,
    template:template,
    controllerAs:"ctrl",
    abstract: true,
    resolve: {
        user: UserResolver
    },
    data: {
        needAuthorize: false
    }
};

export {AppState}