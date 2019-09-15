import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Person } from './person';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class PersonService extends EntityService<Person> {
  constructor($http: HttpClient) {
    super($http);
  }
}
