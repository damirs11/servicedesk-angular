import { Entity } from '../entity/entity';
import { EntityStatus } from '../entity-status/entity-status';
import { Folder } from '../folder/folder';

/**
 * Сервис/услуга
 */
export class Service extends Entity {
/**
   * Статус
   * @property
   * @type {EntityStatus}
   */
  status: EntityStatus;

  /**
   * Папка
   * @type {Folder}
   */
  folder: Folder;
}
