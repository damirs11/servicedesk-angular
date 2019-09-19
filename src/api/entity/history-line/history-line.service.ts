import { Injectable } from "@angular/core";
import { EntityModule } from "src/api/entity.module";
import { EntityService } from '../entity/entity.service';
import { HistoryLine } from './history-line';
import { HttpClient } from '@angular/common/http';
import { EntityTypes } from 'src/api/util/entity-types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: EntityModule
})
export class HistoryLineService extends EntityService {
  constructor($http: HttpClient) {
    super($http);
  }


  /**
   * Возвращает коллекцию записей в истории
   */
  getHistory(params: object): Observable<HistoryLine[]> {
    return this.get(`rest/service/history`, params);
  }

  /**
   * Получает общее количество записей по указанному фильтру
   */
  // todo удалить метод, вместо этого в методе getHistory извлекать данное значение
  // из Header-параметра "Content-Range". Формат "from-to/amount". Например: "26-50/456"
  // означает, чтобы были возвращены данные с 26 по 50 запись включительно, всего записей 456
  getHistoryCount(id: number, entityType: EntityTypes, params: object): Observable<number> {
    return this.get(`rest/entity/${entityType}/${id}/history/count`, params);
  }

  /**
   * Возвращает записи чата
   */
  getChat(params: {chat? : boolean, sort? : string}): Observable<HistoryLine[]> {
    params.chat = true;
    params.sort = "date-asc";
    return this.getHistory(params);
  }

  /**
   * Возвращает количество сообщений в чате
   */
  getChatCount(id: number, entityType: EntityTypes, params: {chat ?: boolean}): Observable<number> {
    params.chat = true;
    return this.getHistoryCount(id, entityType, params);
  }

  /**
   * Отправляет сообщение в чат сущности.
   * @param text {String} - текст сообщения
   * @param type {String} - тип сообщения (инициатору, исполнителю и т.п.)
   */
  sendChatMessage(id: number, entityType: EntityTypes, text: any, type: {name? : string}): void {
    this.post(`rest/service/history`, JSON.stringify({entityType: entityType, entityId: id, historyType: type.name}), text);
  }
}
