import {ConnectorRespError} from "./connector-rejection";

function ConnectorConfig($connectorProvider) {

    const errorHandler = (error,Session,$state,$injector) => {
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
        throw new ConnectorRespError("Не удалось выполнить запрос к серверу",error.data,error.status);
    };
    errorHandler.$inject = ["error","Session","$state","$injector"];

    $connectorProvider.setErrorHandler(errorHandler)
}
ConnectorConfig.$inject = ["$connectorProvider"];

// Обрабатывает промисы, которые никто не обработал
function HandlePromiseRejection() {

}

export {ConnectorConfig}