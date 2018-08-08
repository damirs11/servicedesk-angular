WorkorderIdResolver.$inject = ["$stateParams"];
function WorkorderIdResolver($stateParams){
    return $stateParams["workorderId"];
}

WorkorderGetterResolver.$inject = ["workorderId","SD"];
function WorkorderGetterResolver(workorderId, SD){
    return () => new SD.Workorder(workorderId);
}

export {WorkorderIdResolver, WorkorderGetterResolver}