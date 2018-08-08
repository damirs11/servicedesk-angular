ChangeIdResolver.$inject = ["$stateParams"];
function ChangeIdResolver($stateParams){
    return $stateParams["changeId"];
}

ChangeGetterResolver.$inject = ["changeId","SD"];
function ChangeGetterResolver(changeId,SD){
    return () => {
        return new SD.Change(changeId)
    }
}

export {ChangeIdResolver,ChangeGetterResolver}