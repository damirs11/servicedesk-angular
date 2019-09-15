import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { ApproverVote } from './approver-vote';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ApproverVoteService extends EntityService<ApproverVote> {
  constructor($http: HttpClient) {
    super($http);
  }
}
