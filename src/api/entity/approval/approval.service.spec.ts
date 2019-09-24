import { TestBed, getTestBed } from "@angular/core/testing";
import { ApprovalService } from './approval.service';
import { HttpClientModule, HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { User } from '../user/user';
import { Approval } from './approval';
// import 'jasmine';

describe('Approval', () => {

    let injector: TestBed;
    let service: ApprovalService;
    let $http: HttpClient;

    const REST_ROOT = "https://localhost:8443/sd/rest";
    const LOGIN_DATA = {
        login: "testadmin",
        password: "XSW@zaq1"
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientModule],
            providers: [ApprovalService]
        });
        injector = getTestBed();
        service = injector.get(ApprovalService);
        $http = injector.get(HttpClient);
    });

    afterEach(() => {
    });

    describe('Должен проверить сессию и залогиниться, если сессия потухла. Возвращает код 200', () => {
        it('Логин', () => {
            service.get(`${REST_ROOT}/service/config/getInfo`).subscribe((status: any) => {
                if (status.user == null) {
                    service.post(`${REST_ROOT}/service/security/login`, JSON.stringify(LOGIN_DATA), { headers, observe: 'response' })
                    .subscribe((res: HttpResponse<User>) => {
                        expect(res.status).toBe(200);
                    });
                }
            });
        });
    });
});
