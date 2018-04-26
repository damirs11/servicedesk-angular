ConnectorConfig.$inject = ["$connectorProvider"];
function ConnectorConfig($connectorProvider) {

    const errorHandler = (error,Session,$state,$q) => {
        if (error.status === 401) {
            if ('GULP_REPLACE:DEBUG') console.error("Got 401 response code.",error.toString());
            Session.user = null;
            const target = $state.current;
            const returnUrl = $state.href(target.name, target.params);
            $state.go("app.login", {returnUrl}, {reload: true});
            return;
        } else if (error.status === -1) {
            console.log("Server unreachable")
            // alert('Сервер недоступен');
        }
        throw error;
    };
    errorHandler.$inject = ["error","Session","$state","$q"];

    $connectorProvider.setErrorHandler(errorHandler)
}

export {ConnectorConfig}