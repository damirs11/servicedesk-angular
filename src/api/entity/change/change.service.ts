import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { Change } from './change';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class ChangeService extends EntityService<Change> {
  constructor($http: HttpClient) {
    super($http);
  }
}
