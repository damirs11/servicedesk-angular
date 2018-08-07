ProblemIdResolver.$inject = ["$stateParams"];
function ProblemIdResolver($stateParams){
    return $stateParams["problemId"];
}

ProblemClassResolver.$inject = ["SD"];
function ProblemClassResolver(SD){
    return SD.Problem;
}

export {ProblemIdResolver, ProblemClassResolver}