import { HttpClient } from "@angular/common/http";
import { timeout, catchError } from "rxjs/operators";
import { throwError, Observable } from "rxjs";
import { IConnector } from "src/api/interfaces/IConnector";
import { EntityTypes } from "src/api/util/entity-types";

export abstract class EntityService<T> implements IConnector<T> {
  constructor(private $http: HttpClient) {}

  readonly emptyParams = [];
  readonly defaultTimeout = 2000;

  /**
   * Отправляет GET запрос к серверу. Вернет промис,
   * в котором можно работать сразу с json данными
   * @param path - относительный путь
   * @param params - параметры для get запроса
   * @param timeout - время ожидания ответа
   */
  public get<C>(
    path: string,
    params: object = this.emptyParams,
    _timeout: number = this.defaultTimeout
  ): Observable<C> {
    return this.$http.get<C>(path, params).pipe(
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
  public post<C>(
    path: string,
    data: string,
    params: object = this.emptyParams,
    _timeout: number = this.defaultTimeout
  ): Observable<C> {
    return this.$http.post<C>(path, data, params).pipe(
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
  public put<C>(
    path: string,
    data: string,
    params: object = this.emptyParams,
    _timeout: number = this.defaultTimeout
  ): Observable<C> {
    return this.$http.put<C>(path, data, params).pipe(
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
  public patch<C>(
    path: string,
    data: string,
    params: object = this.emptyParams,
    _timeout: number = this.defaultTimeout
  ): Observable<C> {
    return this.$http.patch<C>(path, data, params).pipe(
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
  public delete<C>(
    path: string,
    params: object = this.emptyParams,
    _timeout: number = this.defaultTimeout
  ): Observable<C> {
    return this.$http.delete<C>(path, params).pipe(
      timeout(_timeout),
      catchError(this.handleError)
    );
  }

  /**
   * Подгружает изменения в текущую сущность
   */
  public load($entityType: EntityTypes, id: number) {
    return this.get<T>(`rest/entity/${$entityType}/${id}`);
  }

  /**
   * Осуществляет поиск сущностей по фильтру
   */
  public list($entityType: EntityTypes, params: object) {
    return this.get<T[]>(`rest/entity/${$entityType}`, params);
  }

  /**
   * Получает общее количество записей по указанному фильтру
   */
  public count($entityType: EntityTypes, params: object) {
    return this.get<number>(`rest/entity/${$entityType}/count`, params);
  }

  /**
   * Заполняет сущность по шаблону
   * @param template {SD.Template|number} - шаблон или id шаблона
   * @return {Promise.<Entity>}
   *
   * TODO поменять тип у template
   */
  public fillWithTemplate($entityType: EntityTypes, template: any) {
    const templateId = typeof template === "object" ? template.id : template;
    return this.get<T>(`rest/entity/${$entityType}/template/${templateId}`);
  }

  // Error handling
  handleError(error: ErrorEvent) {
    let errorMessage = "";
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.error}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
