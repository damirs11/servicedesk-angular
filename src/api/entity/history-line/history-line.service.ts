import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { HistoryLine } from './history-line';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class HistoryLineService extends EntityService<HistoryLine> {
  constructor($http: HttpClient) {
    super($http);
  }
}
