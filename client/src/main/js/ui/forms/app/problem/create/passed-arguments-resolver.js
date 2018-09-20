function PassedParamsResolver($stateParams){
    const passedParams = {
        templateId: $stateParams.templateId || undefined,
    };
    return passedParams
}
PassedParamsResolver.$inject = ["$stateParams"];
export {PassedParamsResolver};