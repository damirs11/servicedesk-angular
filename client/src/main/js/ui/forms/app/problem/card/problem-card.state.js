/**
 * Абстрактный стейт отвечающий за работу с конкретной "проблемой".
 */
import {SDResolver} from "../../sd.resolver";
import {controller} from "./problem-card.ctrl"
import template from "./problem-card.html"
import {ProblemIdResolver} from "./problem.resolver";

let ProblemCardState = {
    name: "app.problem.card",
    url: "/{problemId:int}",
    controller: controller,
    template: template,
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver,
        problemId: ProblemIdResolver
    }
};

export {ProblemCardState};