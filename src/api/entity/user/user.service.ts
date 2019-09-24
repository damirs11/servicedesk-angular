import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { User } from './user';
import { EntityService } from '../entity/entity.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class UserService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
