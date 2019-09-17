import { Observable } from "rxjs";

// TODO В будущем поменять тип @params и @_timeout
export interface IConnector<T> {
  get<ENTITY>(
    path: string,
    params?: object,
    _timeout?: number
  ): Observable<ENTITY>;
  post<ENTITY>(
    path: string,
    data: string,
    params?: object,
    _timeout?: number
  ): Observable<ENTITY>;
  put<ENTITY>(
    path: string,
    data: string,
    params?: object,
    _timeout?: number
  ): Observable<ENTITY>;
  patch<ENTITY>(
    path: string,
    data: string,
    params?: object,
    _timeout?: number
  ): Observable<ENTITY>;
  delete<ENTITY>(
    path: string,
    params?: object,
    _timeout?: number
  ): Observable<ENTITY>;
}
