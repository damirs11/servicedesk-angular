import {SDResolver} from "../sd.resolver";

/**
 * Абстрактный базовый стейт для заявок
 */
let ServiceCallState = {
    name: "app.servicecall",
    url: "/servicecall",
    controllerAs: "ctrl",
    controller: "",
    abstract: true,
    resolve: {
        SD: SDResolver
    }
};

export {ServiceCallState};