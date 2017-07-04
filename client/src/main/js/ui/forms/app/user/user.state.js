import {UserController as controller} from "./user.ctrl.js";

/**
 * Все стейты, которые будут наследоваться от него - будут автоматически проверять залогинен ли юзер;
 */

let UserState = {
    name:"app.user",
    url:"user",
    controller:controller,
    controllerAs:"ctrl",
    abstract: true
};

export {UserState};