import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from "../entity/entity.service";
import { Attachment } from "./attachment";
import { HttpClient } from "@angular/common/http";
import { FileInfo } from '../file-info/file-info';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: EntityModule
})
export class AttachmentService extends EntityService<Attachment> {
  constructor($http: HttpClient) {
    super($http);
  }

  /**
   * Возвращает список вложений
   * @return {SD.Attachment[]}
   */
  getAttachments(id: number, params: object = {}): Observable<Attachment[]> {
    return this.get(`rest/entity/${Attachment.$entityType}?entityId=${id}`, params);
  }

  /**
   * Получает количество вложений по переданному запросу
   * @return {Number}
   */
  getAttachmentsCount(id: number, params: object = {}): Observable<number> {
    return this.get(`rest/entity/${Attachment.$entityType}/count?entityId=${id}`, params);
  }

  /**
   * Прикрепляет файл к сущности.
   * @param fileInfo {SD.FileInfo} - прикрепляемый файл
   * @return {Promise.<SD.Attachment>}
   */
  attachFile(id: number, fileInfo: FileInfo): Observable<Attachment> {
    return this.post(
      `rest/entity/${Attachment.$entityType}`,
      null,
      { entityId: id, entityType: Attachment.$entityType, path: fileInfo.path }
    );
  }
}
