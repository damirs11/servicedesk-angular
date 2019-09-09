class ConnectorRespError extends Error{
    name = "ConnectorRespError";
    responseData;
    responseStatus;

    constructor(message, data, status) {
        super(message);
        this.responseData = data;
        this.responseStatus = status;
    }
}


function handleConnectorRejection(error, ModalAction, $rootScope) {
    ModalAction.connectorError($rootScope,{error});
}
handleConnectorRejection.$inject = ["error", "ModalAction", "$rootScope"];


if ('GULP_REPLACE:DEBUG') window._ConnectorRespError = ConnectorRespError;

export {ConnectorRespError, handleConnectorRejection}