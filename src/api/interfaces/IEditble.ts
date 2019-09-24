import { Observable } from 'rxjs';

export interface IEditble {
    save<ENTITY>(data: string): Observable<ENTITY>;
    create<ENTITY>(): Observable<ENTITY>;
    delete(): void;
}
