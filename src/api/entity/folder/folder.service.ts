import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { Folder } from './folder';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class FolderService extends EntityService<Folder> {
  constructor($http: HttpClient) {
    super($http);
  }
}
