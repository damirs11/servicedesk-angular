import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { FileInfo } from './file-info';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: EntityModule
})
export class FileInfoService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }

  /**
  * Загружает файл на сервер.
  * @static
  * @method
  * @param file {File} - HTML File объект
  */
  // static async upload(file) {
  //     const uploading = Upload.upload({
  //         method: "POST",
  //         url: "rest/service/file/upload",
  //         data: {"uploaded-file": file}
  //     });

  //     return uploading.then(resp => this.parse(resp.data));
  // }
}
