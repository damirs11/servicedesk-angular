ProblemIdResolver.$inject = ["$stateParams"];
function ProblemIdResolver($stateParams){
    return $stateParams["problemId"];
}

ProblemGetterResolver.$inject = ["problemId","SD"];
function ProblemGetterResolver(problemId,SD){
    return () => new SD.Problem(problemId);
}

export {ProblemIdResolver, ProblemGetterResolver}