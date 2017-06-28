
Connector.$inject = ["$http","config"];
export class Connector{

    constructor($http, config){
        this.address = config.address || "";
        this.$http = $http;
    }

    _getDestination(path){
        return `${this.address}/${path}`
    }

    /**
     * Отправляет GET запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param timeout - время ожидания ответа
     * @param params - параметры для get запроса
     */
    async get(path, timeout, params){
        const destination = this._getDestination(path);
        const config = {};
        if (timeout != null) config.timeout = timeout;
        if (params != null) config.params = params;
        try {
            const response = await this.$http.get(destination, config);
            return response;
        } catch (errorResponse) {
            // ToDo придумать структуру для ошибок, и throw-ать свои ошибки тут
            throw errorResponse
        }
    }

    /**
     * Отправляет POST запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param timeout - время ожидания ответа
     * @param data - json данные, для отправки
     */
    async post(path, timeout, data){
        const destination = this._getDestination(path);
        const config = {};
        if (timeout != null) config.timeout = timeout;
        if (data != null) config.data = data;
        try {
            const response = await this.$http.get(destination, config);
            return response;
        } catch (errorResponse) {
            // ToDo придумать структуру для ошибок, и throw-ать свои ошибки тут
            throw errorResponse
        }
    }
}