import { Entity } from '../entity/entity';
import { EntityStatus } from '../entity-status/entity-status';
import { Folder } from '../folder/folder';

/**
 * Сервис/услуга
 * @class
 * @name Service
 */
export class Service extends Entity {
/**
   * Статус
   * @property
   * @name Service#status
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Папка
   * @property
   * @name Service#folder
   * @type {Folder}
   */
  folder: Folder;
}
