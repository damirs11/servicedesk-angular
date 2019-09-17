import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { ServiceLevel } from './service-level';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ServiceLevelService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
