ServiceCallIdResolver.$inject = ["$stateParams"];
function ServiceCallIdResolver($stateParams){
    return $stateParams["serviceCallId"];
}

ServiceCallGetterResolver.$inject = ["serviceCallId", "SD"];
function ServiceCallGetterResolver(serviceCallId, SD){
    return () => {
        return new SD.ServiceCall(serviceCallId);
    };
}

export {ServiceCallIdResolver, ServiceCallGetterResolver};