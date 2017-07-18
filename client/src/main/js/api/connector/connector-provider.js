import {Connector} from "./connector";

// $http нужен внутри Connector'а
function ConnectorProvider() {
    /**
     * Адрес сервера с API
     * @type
     */
    let address = "/sd";
    /**
     * Обработчик ошибок ajax запросов.
     * @type {ngFunction}
     */
    let errorHandler = null;

    const provider = {
        setErrorHandler(handler){
            errorHandler = handler;
        },
        setAddress(addr){
            address = addr
        },
        $get($injector){
            return $injector.instantiate(Connector, {address, errorHandler})
        }
    };
    provider.$get.$inject = ["$injector"];
    return provider;
}

export {ConnectorProvider};