import { Entity } from '../entity/entity';

/**
 * Сервис/услуга
 * @class
 * @name Service
 */
export class Service extends Entity {
  /**
   * Название
   * @property
   * @name Service#name
   * @type {string}
   */
  name: string;

  /**
   * Статус
   * @property
   * @name Service#status
   * @type {EntityStatus}
   */
  status;

  /**
   * Папка
   * @property
   * @name Service#folder
   * @type {Folder}
   */
  folder;

  toString() {
    return String(this.name);
  }
}
