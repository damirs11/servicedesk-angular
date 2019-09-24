import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Organization } from './organization';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class OrganizationService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
