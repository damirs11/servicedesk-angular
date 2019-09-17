import { Entity } from "../entity/entity";
import { EntityStatus } from "../entity-status/entity-status";
import { Folder } from "../folder/folder";

/**
 * Сервис/услуга
 */
export class Service extends Entity {
  /**
   * Статус
   */
  status: EntityStatus;

  /**
   * Папка
   */
  folder: Folder;
}
