import { TestBed, getTestBed } from "@angular/core/testing"
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

import { EntityService } from './entity.service';
import { Entity } from './entity';


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

    describe('get<Entity>', () => {
        it('должен вернуть Observable<Entity>', () => {
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
        })
    });
});