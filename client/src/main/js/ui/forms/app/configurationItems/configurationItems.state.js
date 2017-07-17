import {ConfigurationItemsController as controller} from "./configurationItems.ctrl.js";
import template from "./configurationItems.html";
import {SDResolver} from "../sd.resolver";

let ConfigurationItemsState = {
    name: "app.configurationItems",
    url: "/configurationItems",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        SD: SDResolver
    }
};

export {ConfigurationItemsState};