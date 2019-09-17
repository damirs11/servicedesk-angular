import { Entity } from '../entity/entity';

/**
 * @class
 * @extends Entity
 * @name FileInfo
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
