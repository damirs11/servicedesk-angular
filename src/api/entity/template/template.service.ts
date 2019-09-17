import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Template } from '@angular/compiler/src/render3/r3_ast';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class TemplateService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}