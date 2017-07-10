import template from "./person.html"
import {controller} from "./person.ctrl"

const PersonState = {
    name:"app.person",
    url:"/person/{personId:int}",
    controller:controller,
    template:template,
    controllerAs:"ctrl",
    data: {
        needAuthorize: true
    }
};

export {PersonState}