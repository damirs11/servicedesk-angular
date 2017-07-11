PersonIdResolver.$inject = ["SD","$stateParams"];
export function PersonIdResolver(SD, $stateParams){
    return $stateParams["personId"];
}