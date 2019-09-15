import { Observable } from "rxjs";

// TODO В будущем поменять тип @params и @_timeout
export interface IConnector<T> {
  get(path: string, params: object, _timeout: number): Observable<T>;
  post(
    path: string,
    data: string,
    params: object,
    _timeout: number
  ): Observable<T>;
  put(
    path: string,
    data: string,
    params: object,
    _timeout: number
  ): Observable<T>;
  patch(
    path: string,
    data: string,
    params: object,
    _timeout: number
  ): Observable<T>;
  delete(path: string, params: object, _timeout: number): Observable<T>;
}
