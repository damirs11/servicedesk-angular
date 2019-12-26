import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Color } from './Color';
import { catchError } from 'rxjs/operators';



@Injectable({
  providedIn: EntityModule
})
export class WorkorderService extends EntityService {

  constructor($http: HttpClient) {
    super($http);
  }

  // TODO: Сделал для тестов, убрать потом
  public getColors(): Observable<Color[]> {
    return this.get<Color[]>("http://localhost:3000/colors");
  }
}


