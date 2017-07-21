ChangeIdResolver.$inject = ["$stateParams"];
function ChangeIdResolver($stateParams){
    return $stateParams["changeId"];
}

export {ChangeIdResolver}