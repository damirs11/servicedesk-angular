import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { ImpactSettings } from './impact-settings';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ImpactSettingsService extends EntityService<ImpactSettings> {
  constructor($http: HttpClient) {
    super($http);
  }
}
