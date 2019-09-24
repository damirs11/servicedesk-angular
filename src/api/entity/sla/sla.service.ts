import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { ServiceLevelAgreement } from './sla';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class SlaService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
