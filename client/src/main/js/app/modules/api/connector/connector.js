export class Connector{

    static $inject = ["config","$http","$state"];

    constructor(config,$http,$state){
        this.address = config.address || "";
        this.$http = $http;
        this.$state = $state;
    }

    _getDestination(path){
        return `${this.address}/${path}`
    }

    /**
     * Отправляет GET запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    async get(path, params, timeout){
        const destination = this._getDestination(path);
        try {
            const response = await this.$http.get(destination, {params,timeout});
            return response.data;
        } catch (errorResponse) {
            throw errorResponse
        }
    }

    /**
     * Отправляет POST запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для запроса
     * @param data - json данные, для отправки
     * @param timeout - время ожидания ответа
     */
    async post(path, params, data, timeout){
        const destination = this._getDestination(path);
        try {
            const response = await this.$http.post(destination, data, {params, timeout});
            return response.data;
        } catch (errorResponse) {
            // ToDo придумать структуру для ошибок, и throw-ать свои ошибки тут
            throw errorResponse
        }
    }
}