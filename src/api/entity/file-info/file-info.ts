import { Entity } from '../entity/entity';

/**
 * @class
 * @extends RESTEntity
 * @name FileInfo
 * Информация о файле. Не имеет своего ID, поэтому кэшируется по fileId
 * Используется в основном для загрузки файла на сервер. В других случаях используется Attachment
 * @see Attachment
 */
export class FileInfo extends Entity {
  /**
   * Переопределяем трекер для FileInfo
   * Т.к. fileInfo не имеет ID, она трекерится по
   */
  static $tracker = {
    ownField: "fileId",
    dataField: "fileId"
  };
  /**
   * Путь на сервере
   * @property
   * @name FileInfo#path
   * @type {String}
   */
  path: string;
}
