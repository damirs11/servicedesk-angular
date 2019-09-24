import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';
import { FAQ } from './faq';

@Injectable({
  providedIn: EntityModule
})
export class FaqService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
