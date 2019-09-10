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
            const $connector = $injector.instantiate(Connector, {errorHandler});
            if ('GULP_REPLACE:DEBUG') window._$connector = $connector;
            return $connector
        }
    };
    provider.$get.$inject = ["$injector"];
    return provider;
}

export {ConnectorProvider};