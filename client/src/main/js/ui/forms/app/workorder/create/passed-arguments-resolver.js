function PassedParamsResolver($stateParams){
    const passedParams = {
        changeId: $stateParams.changeId || undefined,
        problemId: $stateParams.problemId || undefined,
        templateId: $stateParams.templateId || undefined,
    };
    return passedParams
}
PassedParamsResolver.$inject = ["$stateParams"];
export {PassedParamsResolver};