import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Source } from './source';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class SourceService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
