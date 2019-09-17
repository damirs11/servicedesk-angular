import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';
import { Problem } from './problem';

@Injectable({
  providedIn: EntityModule
})
export class ProblemService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
