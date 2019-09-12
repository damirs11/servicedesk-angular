import { Observable } from 'rxjs';

export interface IConnector<T> {
    get   <T>   (path: any, params?: any, _timeout?: any);
    post  <T>  (path: any, data: any, params?: any,  _timeout?: any);
    put   <T>  (path: any, data: any, params?: any,  _timeout?: any);
    patch <T>  (path: any, data: any, params?: any,  _timeout?: any);
    delete<T>  (path: any, params?: any, _timeout?: any);
}