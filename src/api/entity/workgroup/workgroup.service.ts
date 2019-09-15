import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Workgroup } from './workgroup';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class WorkgroupService extends EntityService<Workgroup> {
  constructor($http: HttpClient) {
    super($http);
  }
}
