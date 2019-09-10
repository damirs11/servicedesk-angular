class Connector {
    // ToDo переделать @NGInjectClass
    static $inject = ["$http", "$state", "$injector", "errorHandler"];
    constructor($http, $state, $injector, errorHandler) {
        this.errorHandler = errorHandler;
        this.$http = $http;
        this.$state = $state;
        this.$injector = $injector;
    }

    _getDestination(path) {
        return path
    }

    /**
     * Отправляет GET запрос к серверу. Вернет промис,
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    async get(path, params, timeout) {
        try {
            const response = await this.$http.get(path, {params, timeout});
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
        try {
            const response = await this.$http.post(path, data, {params, timeout});
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
        try {
            const response = await this.$http.put(path, data, {params, timeout});
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
        try {
            const response = await this.$http.patch(path, data, {params, timeout});
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
     * Отправляет DELETE запрос к серверу.
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    async delete(path, params, timeout) {
        try {
            const response = await this.$http.delete(path, {params, timeout});
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