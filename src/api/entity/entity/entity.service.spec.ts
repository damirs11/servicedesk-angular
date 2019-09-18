import { TestBed, getTestBed } from "@angular/core/testing";
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

import { EntityService } from './entity.service';
import { Entity } from './entity';
import { EntityTypes } from 'src/api/util/entity-types';
import { ServiceLevel } from '../service-level/service-level';

describe('Entity', () => {
    const REST_ROOT = "rest/entity";

    let injector: TestBed;
    let service: EntityService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [EntityService]
        });
        injector = getTestBed();
        service = injector.get(EntityService);
        httpMock = injector.get(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    describe('get<Entity[]>', () => {
        it('должен вернуть Observable<Entity[]>', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.get<Entity[]>(`${REST_ROOT}/test`).subscribe(entitys => {
                expect(entitys.length).toBe(2);
                expect(entitys).toEqual(dummyEntity);
            });

            const req = httpMock.expectOne(`${REST_ROOT}/test`);
            expect(req.request.method).toBe("GET");
            req.flush(dummyEntity);
        });
    });
    
    describe('post<Entity[]>', () => {
        it('должен вернуть Observable<Entity[]>', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.post<Entity[]>(`${REST_ROOT}/test`, null).subscribe(entitys => {
                expect(entitys.length).toBe(2);
                expect(entitys).toEqual(dummyEntity);
            });

            const req = httpMock.expectOne(`${REST_ROOT}/test`);
            expect(req.request.method).toBe("POST");
            req.flush(dummyEntity);
        });
    });

    describe('put<Entity[]>', () => {
        it('должен вернуть Observable<Entity[]>', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.put<Entity[]>(`${REST_ROOT}/test`, null).subscribe(entitys => {
                expect(entitys.length).toBe(2);
                expect(entitys).toEqual(dummyEntity);
            });

            const req = httpMock.expectOne(`${REST_ROOT}/test`);
            expect(req.request.method).toBe("PUT");
            req.flush(dummyEntity);
        });
    });

    describe('patch<Entity[]>', () => {
        it('должен вернуть Observable<Entity[]>', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.patch<Entity[]>(`${REST_ROOT}/test`, null).subscribe(entitys => {
                expect(entitys.length).toBe(2);
                expect(entitys).toEqual(dummyEntity);
            });

            const req = httpMock.expectOne(`${REST_ROOT}/test`);
            expect(req.request.method).toBe("PATCH");
            req.flush(dummyEntity);
        });
    });

    describe('delete<Entity[]>', () => {
        it('должен вернуть Observable<Entity[]>', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.delete<Entity[]>(`${REST_ROOT}/test`).subscribe(entitys => {
                expect(entitys.length).toBe(2);
                expect(entitys).toEqual(dummyEntity);
            });

            const req = httpMock.expectOne(`${REST_ROOT}/test`);
            expect(req.request.method).toBe("DELETE");
            req.flush(dummyEntity);
        });
    });

    describe('load<Entity>', () => {
        it('должен вернуть Entity', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.load<Entity>(EntityTypes.Change, 1).subscribe((ent: Entity) => {
                expect(ent).toBe(dummyEntity[0]);
            });

            const req = httpMock.expectOne(`rest/entity/${EntityTypes.Change}/${1}`);
            expect(req.request.method).toBe("GET");
            req.flush(dummyEntity[0]);
        });
    });

    describe('list<Entity>', () => {
        it('должен вернуть Entity[]', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.list<Entity>(EntityTypes.Change, {}).subscribe((ent: Entity[]) => {
                expect(ent).toBe(dummyEntity);
            });

            const req = httpMock.expectOne(`rest/entity/${EntityTypes.Change}`);
            expect(req.request.method).toBe("GET");
            req.flush(dummyEntity);
        });
    });

    describe('count', () => {
        it('должен вернуть кол-во Entity', () => {
            const dummyEntity: Entity[] = [
                {name: "NAME_1"},
                {name: "NAME_2"}
            ];

            service.count(EntityTypes.Change, {}).subscribe((ent: number) => {
                expect(ent).toBe(dummyEntity.length);
            });

            const req = httpMock.expectOne(`rest/entity/${EntityTypes.Change}/count`);
            expect(req.request.method).toBe("GET");
            req.flush(dummyEntity.length);
        });
    });
});
