SearchParamsResolver.$inject = ["$stateParams"];
function SearchParamsResolver($stateParams){
    const params = {};
    const searchParams = ["sortBy"];
    searchParams.forEach(name => {
        params[name] = $stateParams[name]
    });
    return params;
}

export {SearchParamsResolver}