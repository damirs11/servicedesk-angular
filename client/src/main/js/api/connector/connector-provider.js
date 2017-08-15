import {Connector} from "./connector";

function ConnectorProvider() {
    /**
     * Обработчик ошибок ajax запросов.
     * @type {ngFunction}
     */
    let errorHandler = null;

    const provider = {
        setErrorHandler(handler){
            errorHandler = handler;
        },
        $get($injector){
            return $injector.instantiate(Connector, {errorHandler})
        }
    };
    provider.$get.$inject = ["$injector"];
    return provider;
}

export {ConnectorProvider};