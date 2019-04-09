PassedParamsResolver.$inject = ["$stateParams"];
function PassedParamsResolver($stateParams){
    const passedParams = {
        templateId: $stateParams.templateId || undefined,
    };
    return passedParams;
}

export {PassedParamsResolver};