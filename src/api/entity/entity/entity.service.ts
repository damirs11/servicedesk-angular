import { HttpClient } from "@angular/common/http";
import { timeout, catchError, first } from "rxjs/operators";
import { throwError, Observable } from "rxjs";
import { IConnector } from "src/api/interfaces/IConnector";
import { EntityTypes } from "src/api/util/entity-types";
import { Injectable } from '@angular/core';

type Template = string | {id: string}

@Injectable()
export abstract class EntityService implements IConnector {
  constructor(private $http: HttpClient) {}
  
  readonly DEFAULT_TIMEOUT = 2000;

  /**
   * Отправляет GET запрос к серверу. Вернет промис,
   * в котором можно работать сразу с json данными
   * @param path - относительный путь
   * @param params - параметры для get запроса
   * @param timeout - время ожидания ответа
   */
  public get<ENTITY>(path: string, params: object = {}, _timeout: number = this.DEFAULT_TIMEOUT): Observable<ENTITY> {
    return this.$http.get<ENTITY>(path, params).pipe(
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
  public post<ENTITY>(path: string, data: string, params: object = {}, _timeout: number = this.DEFAULT_TIMEOUT): Observable<ENTITY> {
    return this.$http.post<ENTITY>(path, data, params).pipe(
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
  public put<ENTITY>(path: string, data: string, params: object = {}, _timeout: number = this.DEFAULT_TIMEOUT): Observable<ENTITY> {
    return this.$http.put<ENTITY>(path, data, params).pipe(
      timeout(_timeout),
      catchError(this.handleError)
    );
  }

  /**
   * Отправляет PATENTITYH запрос к серверу. Вернет промис
   * в котором можно работать сразу с json данными
   * @param path - относительный путь
   * @param params - параметры для запроса
   * @param data - json данные, для отправки
   * @param timeout - время ожидания ответа
   */
  public patch<ENTITY>(path: string, data: string, params: object = {}, _timeout: number = this.DEFAULT_TIMEOUT): Observable<ENTITY> {
    return this.$http.patch<ENTITY>(path, data, params).pipe(
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
  public delete<ENTITY>(path: string, params: object = {}, _timeout: number = this.DEFAULT_TIMEOUT): Observable<ENTITY> {
    return this.$http.delete<ENTITY>(path, params).pipe(
      timeout(_timeout),
      catchError(this.handleError)
    );
  }

  /** Подгружает изменения в текущую сущность */
  public load<ENTITY>(entityType: EntityTypes, id: number) {
    return this.get<ENTITY>(`rest/entity/${entityType}/${id}`);
  }

  /** Осуществляет поиск сущностей по фильтру */
  public list<ENTITY>(entityType: EntityTypes, params: object) {
    return this.get<ENTITY[]>(`rest/entity/${entityType}`, params);
  }

  /** Получает общее количество записей по указанному фильтру */
  public count(entityType: EntityTypes, params: object) {
    return this.get<number>(`rest/entity/${entityType}/count`, params);
  }

  /**
   * Заполняет сущность по шаблону
   * @param template {SD.Template|number} - шаблон или id шаблона
   */
  public fillWithTemplate<ENTITY>(entityType: EntityTypes, template: Template) {
    const templateId = typeof template === "object" ? template.id : template;
    return this.get<ENTITY>(`rest/entity/${entityType}/template/${templateId}`);
  }

  // Error handling
  handleError(error: ErrorEvent): Observable<never> {
    let errorMessage = "";
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error ENTITYode: ${error.error}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
