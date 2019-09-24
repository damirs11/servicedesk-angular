import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { EntityStatus } from './entity-status';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityStatusService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}