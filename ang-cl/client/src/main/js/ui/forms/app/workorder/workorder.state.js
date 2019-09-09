import {SDResolver} from "../sd.resolver";

/**
 * Абстрактный стейт, включающий стейты связанные с нарядами.
 * Необходим для предоставления SD и области видимости кэша в них.
 * У дочерних стейтов кэш SD будет наследоваться от текущего.
 */
let WorkorderState = {
    name: "app.workorder",
    url: "/workorder",
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver
    }
};

export {WorkorderState};