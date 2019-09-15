import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";

@Injectable({
  providedIn: EntityModule
})
export class TemplateService extends EntityService<Template> {
  constructor($http: HttpClient) {
    super($http);
  }
}