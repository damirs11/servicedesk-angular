import { Injectable } from "@angular/core";
import { EntityService } from "../entity/entity.service";
import { Approval } from "./approval";
import { HttpClient } from "@angular/common/http";
import { EntityModule } from "src/api/entity.module";

@Injectable({
  providedIn: EntityModule
})
export class ApprovalService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
