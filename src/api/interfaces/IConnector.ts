import { Observable } from "rxjs";

// TODO В будущем поменять тип @params и @_timeout
export interface IConnector<T> {
  get<C>(
    path: string,
    params?: object,
    _timeout?: number
  ): Observable<C>;
  post<C>(
    path: string,
    data: string,
    params?: object,
    _timeout?: number
  ): Observable<C>;
  put<C>(
    path: string,
    data: string,
    params?: object,
    _timeout?: number
  ): Observable<C>;
  patch<C>(
    path: string,
    data: string,
    params?: object,
    _timeout?: number
  ): Observable<C>;
  delete<C>(
    path: string,
    params?: object,
    _timeout?: number
  ): Observable<C>;
}
