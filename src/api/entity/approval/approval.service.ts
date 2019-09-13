import { Injectable } from '@angular/core';
import { Connector } from '../../connector/connector';
import { Approval } from './approval';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ApprovalService extends Connector<Approval> {
    constructor($http: HttpClient) {
        super($http);
    }
}
