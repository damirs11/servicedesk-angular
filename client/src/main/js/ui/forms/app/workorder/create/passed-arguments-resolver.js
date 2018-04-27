function PassedParamsResolver($stateParams){
    const passedParams = {
        changeId: $stateParams.changeId || null,
    };
    return passedParams
}
PassedParamsResolver.$inject = ["$stateParams"];
export {PassedParamsResolver};