import {SDResolver} from "../sd.resolver";

let ChangeState = {
    name: "app.change",
    url: "/change",
    controllerAs: "ctrl",
    abstract: true,
    resolve: {
        SD: SDResolver
    }
};

export {ChangeState};