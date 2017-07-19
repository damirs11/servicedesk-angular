import {QuestionsController as controller} from "./questions.ctrl.js";
import template from "./questions.html";
import {SDResolver} from "../sd.resolver";

let QuestionsState = {
    name: "app.questions",
    url: "/questions",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    data: {
        needAuthorize: true,
        SD: SDResolver
    }
};

export {QuestionsState};