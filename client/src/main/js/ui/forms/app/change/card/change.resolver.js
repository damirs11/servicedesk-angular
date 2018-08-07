ChangeIdResolver.$inject = ["$stateParams"];
function ChangeIdResolver($stateParams){
    return $stateParams["changeId"];
}

ChangeClassResolver.$inject = ["SD"];
function ChangeClassResolver(SD){
    return SD.Change;
}

export {ChangeIdResolver,ChangeClassResolver}