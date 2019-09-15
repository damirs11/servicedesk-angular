import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityClosureCode } from './entity-closure-code';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityClosureCodeService extends EntityService<EntityClosureCode> {
  constructor($http: HttpClient) {
    super($http);
  }
}