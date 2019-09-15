import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from "../entity/entity.service";
import { Attachment } from "./attachment";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: EntityModule
})
export class AttachmentService extends EntityService<Attachment> {
  constructor($http: HttpClient) {
    super($http);
  }
}
