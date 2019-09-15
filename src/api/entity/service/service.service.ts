import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Service } from './service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ServiceService extends EntityService<Service> {
  constructor($http: HttpClient) {
    super($http);
  }
}
