import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityCode6 } from './entity-code6';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityCode6Service extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}