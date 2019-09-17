import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityPriority } from './entity-priority';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityPriorityService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
