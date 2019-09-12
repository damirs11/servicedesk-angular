import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timeout, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

export class Connector {
    // ToDo переделать @NGInjectClass
    // static $inject = ["$http", "$state", "$injector", "errorHandler"];
    // constructor($http, $state, $injector, errorHandler) {
    //     this.errorHandler = errorHandler;
    //     this.$http = $http;
    //     this.$state = $state;
    //     this.$injector = $injector;
    // }

    constructor(private $http: HttpClient) {
    }

    _getDestination(path) {
        return path;
    }

    /**
     * Отправляет GET запрос к серверу. Вернет промис,
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    public get(path, params, _timeout) {
        return this.$http.get(path, params)
            .pipe(
                timeout(_timeout),
                catchError(this.handleError)
            );
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
                this.$injector.invoke(this.errorHandler, this, {error});
            } else {
                throw error;
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
                this.$injector.invoke(this.errorHandler, this, {error});
            } else {
                throw error;
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
                this.$injector.invoke(this.errorHandler, this, {error});
            } else {
                throw error;
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
                this.$injector.invoke(this.errorHandler, this, {error});
            } else {
                throw error;
            }
        }
    }

    // Error handling
    handleError(error) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
        // Get client-side error
        errorMessage = error.error.message;
        } else {
        // Get server-side error
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        window.alert(errorMessage);
        return throwError(errorMessage);
    }
}
