import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { ServiceLevelPriorityDuration } from './service-level-priority-duration';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ServiceLevelPriorityDurationService extends EntityService<ServiceLevelPriorityDuration> {
  constructor($http: HttpClient) {
    super($http);
  }
}