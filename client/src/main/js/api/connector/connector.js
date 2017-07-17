class Connector {

    static $inject = ["$http", "$state", "$injector", "address", "errorHandler"];
    constructor($http, $state, $injector, address, errorHandler) {
        this.errorHandler = errorHandler;
        this.address = address || "";
        this.$http = $http;
        this.$state = $state;
        this.$injector = $injector;
    }

    _getDestination(path) {
        return `${this.address}/${path}`
    }

    /**
     * Отправляет GET запрос к серверу. Вернет промис,
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    async get(path, params, timeout) {
        const destination = this._getDestination(path);
        try {
            const response = await this.$http.get(destination, {params, timeout});
            return response.data;
        } catch (error) {
            if (this.errorHandler) {
                this.$injector.invoke(this.errorHandler,this,{error});
            } else {
                throw error
            }
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
    async post(path, params, data, timeout) {
        const destination = this._getDestination(path);
        try {
            const response = await this.$http.post(destination, data, {params, timeout});
            return response.data;
        } catch (error) {
            if (this.errorHandler) {
                this.$injector.invoke(this.errorHandler,this,{error});
            } else {
                throw error
            }
        }
    }

    /**
     * Отправляет PUT запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для запроса
     * @param data - json данные, для отправки
     * @param timeout - время ожидания ответа
     */
    async put(path, params, data, timeout) {
        const destination = this._getDestination(path);
        try {
            const response = await this.$http.put(destination, data, {params, timeout});
            return response.data;
        } catch (error) {
            if (this.errorHandler) {
                this.$injector.invoke(this.errorHandler,this,{error});
            } else {
                throw error
            }
        }
    }


    /**
     * Отправляет PATCH запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для запроса
     * @param data - json данные, для отправки
     * @param timeout - время ожидания ответа
     */
    async patch(path, params, data, timeout) {
        const destination = this._getDestination(path);
        try {
            const response = await this.$http.patch(destination, data, {params, timeout});
            return response.data;
        } catch (error) {
            if (this.errorHandler) {
                this.$injector.invoke(this.errorHandler,this,{error});
            } else {
                throw error
            }
        }
    }
}

export {Connector};