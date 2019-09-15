import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { ServiceCall } from './servicecall';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ServicecallService extends EntityService<ServiceCall> {
  constructor($http: HttpClient) {
    super($http);
  }
}
