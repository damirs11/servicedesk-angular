import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityClassification } from './entity-classification';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityClassificationService extends EntityService<EntityClassification> {
  constructor($http: HttpClient) {
    super($http);
  }
}
