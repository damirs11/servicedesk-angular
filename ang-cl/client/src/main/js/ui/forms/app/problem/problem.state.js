import {SDResolver} from "../sd.resolver";

let ProblemState = {
    name: "app.problem",
    url: "/problem",
    abstract: true,
    resolve: {
        SD: SDResolver,
    }
};

export {ProblemState};