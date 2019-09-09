PersonIdResolver.$inject = ["SD","$stateParams"];
function PersonIdResolver(SD, $stateParams){
    return $stateParams["personId"];
}

export {PersonIdResolver}