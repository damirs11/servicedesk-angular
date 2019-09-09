import {ConnectorRespError, handleConnectorRejection} from "./connector/connector-rejection";
import {handleUIDisplayedError} from "../../utils/ui-displayed-error";

// Кастомный обработчик ошибок.
function SDExceptionHandler($log, $injector) {
    return (error, cause) => {
        let handler = null;
        if (error instanceof Error) switch (error.name) {
            case "ConnectorRespError":
                handler = handleConnectorRejection;
                break;
            case "UIDisplayedError":
                handler = handleUIDisplayedError;
                break;
        }

        if (handler == null){
            $log.error(error,cause);
        } else {
            $injector.invoke(handler, handler, {error, cause})
        }
    }
}
SDExceptionHandler.$inject = ["$log", "$injector"];

export {SDExceptionHandler}