
ReturnURLResolver.$inject = ["$stateParams"];
function ReturnURLResolver($stateParams) {
    return $stateParams.returnUrl
}

export {ReturnURLResolver}