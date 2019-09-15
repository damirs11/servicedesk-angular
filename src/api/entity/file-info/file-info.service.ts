import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { FileInfo } from './file-info';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class FileInfoService extends EntityService<FileInfo> {
  constructor($http: HttpClient) {
    super($http);
  }
}
