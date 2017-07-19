import {AppController as controller} from "./app.ctrl.js";
import template from "./app.html";
import {UserResolver} from "./user.resolver";
import {SDResolver} from "./sd.resolver"

let AppState = {
    name:"app",
    url:"",
    controller:controller,
    template:template,
    controllerAs:"ctrl",
    abstract: true,
    resolve: {
        user: UserResolver,
        SD: SDResolver
    },
    data: {
        needAuthorize: false
    }
};

export {AppState}