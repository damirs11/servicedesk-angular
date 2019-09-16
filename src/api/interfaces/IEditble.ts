import { Observable } from 'rxjs';

export interface IEditble {
    save<C>(data: string): Observable<C>;
    create<C>(): Observable<C>;
    delete(): void;
}
