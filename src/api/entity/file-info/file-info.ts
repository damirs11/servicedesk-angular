import { Entity } from '../entity/entity';

/**
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
   * @type {String}
   */
  path: string;
}
