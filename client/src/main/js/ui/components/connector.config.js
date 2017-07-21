ConnectorConfig.$inject = ["$connectorProvider"];
function ConnectorConfig($connectorProvider) {

    const errorHandler = (error,SD,$state,$q) => {
        if (error.status === 401) {
            SD.user = null;
            $state.go("app.login", null, {reload: true});
        } else if (error.status === -1) {
            alert('Сервер недоступен');
        }
        throw error;
    };
    errorHandler.$inject = ["error","SD","$state","$q"];

    $connectorProvider.setErrorHandler(errorHandler)
}

export {ConnectorConfig}