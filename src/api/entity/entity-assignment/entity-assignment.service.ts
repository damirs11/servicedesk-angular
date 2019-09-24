import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityAssignment } from "./entity-assignment";
import { EntityService } from "../entity/entity.service";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: EntityModule
})
export class EntityAssignmentService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }
}
