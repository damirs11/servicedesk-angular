import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityCode7 } from './entity-code7';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityCode7Service extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
