import template from "./person.html"
import {controller} from "./person.ctrl"
import {PersonIdResolver} from "./person-id.resolver"

const PersonState = {
    name: "app.person",
    url: "/person/{personId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    resolve: {
        personId: PersonIdResolver
    },
    data: {
        needAuthorize: true
    }
};

export {PersonState}