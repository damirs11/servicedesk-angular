WorkorderIdResolver.$inject = ["$stateParams"];
function WorkorderIdResolver($stateParams){
    return $stateParams["workorderId"];
}

export {WorkorderIdResolver}