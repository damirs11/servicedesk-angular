import { Injectable } from '@angular/core';
import { Connector } from '../../connector/connector';
import { Approval } from './approval';

@Injectable({
    providedIn: 'root'
})
export class ApprovalService extends Connector<Approval> {
    constructor() {
        super();
    }
}
