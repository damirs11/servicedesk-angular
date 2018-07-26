ProblemIdResolver.$inject = ["$stateParams"];
function ProblemIdResolver($stateParams){
    return $stateParams["problemId"];
}

export {ProblemIdResolver}