function PassedChangeResolver($stateParams){
    return $stateParams.changeId || null;
}
PassedChangeResolver.$inject = ["$stateParams"];
export {PassedChangeResolver};