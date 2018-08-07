WorkorderIdResolver.$inject = ["$stateParams"];
function WorkorderIdResolver($stateParams){
    return $stateParams["workorderId"];
}

WorkorderClassResolver.$inject = ["SD"];
function WorkorderClassResolver(SD){
    return SD.Workorder;
}

export {WorkorderIdResolver, WorkorderClassResolver}