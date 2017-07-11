import template from "./person.html"
import {controller} from "./person.ctrl"
import {PersonIdResolver} from "./person-id.resolver"

const PersonState = {
    name:"app.person",
    url:"/person/{personId:int}",
    controller:controller,
    template:template,
    controllerAs:"ctrl",
    data: {
        needAuthorize: true
    },
    resolve: {
        personId: PersonIdResolver
    }
};

export {PersonState}