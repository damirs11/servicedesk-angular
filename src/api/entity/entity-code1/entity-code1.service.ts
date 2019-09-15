import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityCode1 } from './entity-code1';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityCode1Service extends EntityService<EntityCode1> {
  constructor($http: HttpClient) {
    super($http);
  }
}