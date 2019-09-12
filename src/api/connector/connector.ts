import { HttpClient } from '@angular/common/http';
import { IConnector } from './IConnector';
import { timeout, catchError } from 'rxjs/operators';
import { throwError} from 'rxjs';


export abstract class Connector<T> implements IConnector<T> {

    constructor(private $http?: HttpClient) {
    }

    /**
     * Отправляет GET запрос к серверу. Вернет промис,
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    public get<T>(path: any, params?: any, _timeout?: any) {
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
    public post<T>(path: any, data: any, params?: any, _timeout?: any) {
        return this.$http.post(path, data, params)
            .pipe(
                timeout(_timeout),
                catchError(this.handleError)
            );
    }

    /**
     * Отправляет PUT запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для запроса
     * @param data - json данные, для отправки
     * @param timeout - время ожидания ответа
     */
    public put<T>(path: any, data: any, params?: any, _timeout?: any) {
        return this.$http.put(path, data, params)
            .pipe(
                timeout(_timeout),
                catchError(this.handleError)
            );
    }


    /**
     * Отправляет PATCH запрос к серверу. Вернет промис
     * в котором можно работать сразу с json данными
     * @param path - относительный путь
     * @param params - параметры для запроса
     * @param data - json данные, для отправки
     * @param timeout - время ожидания ответа
     */
    public patch<T>(path: any, data: any, params?: any, _timeout?: any) {
        return this.$http.patch(path, data, params)
            .pipe(
                timeout(_timeout),
                catchError(this.handleError)
            );
    }

    /**
     * Отправляет DELETE запрос к серверу.
     * @param path - относительный путь
     * @param params - параметры для get запроса
     * @param timeout - время ожидания ответа
     */
    public delete<T>(path: any, params?: any, _timeout?: any) {
        return this.$http.delete(path, params)
            .pipe(
                timeout(_timeout),
                catchError(this.handleError)
            );
    }

    /**
     * Подгружает изменения в текущую сущность
     */
    public load($entityType, id){
        return this.get<T>(`rest/entity/${$entityType}/${id}`);
    }

    /**
     * Осуществляет поиск сущностей по фильтру
     */
    public list($entityType, params){
        return this.get<T>(`rest/entity/${$entityType}`, params);
    }

    /**
     * Получает общее количество записей по указанному фильтру
     */
    public count($entityType, params){
        return this.get<T>(`rest/entity/${$entityType}/count`, params);
    }


    /**
     * Заполняет сущность по шаблону
     * @param template {SD.Template|number} - шаблон или id шаблона
     * @return {Promise.<Entity>}
     */
    public fillWithTemplate($entityType, template){
        const templateId = typeof template === "object" ? template.id : template;
        return this.get(`rest/entity/${$entityType}/template/${templateId}`);
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
