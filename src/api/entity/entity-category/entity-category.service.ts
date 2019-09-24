import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityCategory } from './entity-category';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class EntityCategoryService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
