import {SDResolver} from "../sd.resolver";
import {ReadAccessResolver} from "./read-access.resolver";

let ChangeState = {
    name: "app.change",
    url: "/change",
    controllerAs: "ctrl",
    controller: "",
    abstract: true,
    resolve: {
        SD: SDResolver,
        // readAccess: ReadAccessResolver
    }
};

export {ChangeState};