import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { ServiceLevelPriority } from './service-level-priority';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ServiceLevelPriorityService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
