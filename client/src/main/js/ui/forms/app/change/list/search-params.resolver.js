SearchParamsResolver.$inject = ["$stateParams"];
function SearchParamsResolver($stateParams){
    const params = {};
    const searchParams = ["sort","fulltext","filter","paging"];
    searchParams.forEach(name => {
        if($stateParams[name] != undefined) params[name] = $stateParams[name]
    });
    return params;
}


export {SearchParamsResolver}