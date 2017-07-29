import template from "./change-view.html"
import {controller} from "./change-view.ctrl"
import {ChangeIdResolver} from "./change-id.resolver"

const ChangeViewState = {
    name: "app.change.view",
    url: "/{changeId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    resolve: {
        changeId: ChangeIdResolver
    },
    data: {
        needAuthorize: true
    }
};

export {ChangeViewState}