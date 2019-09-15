import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { ConfigurationItem } from './configuration-item';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ConfigurationItemService extends EntityService<ConfigurationItem> {
  constructor($http: HttpClient) {
    super($http);
  }
}
