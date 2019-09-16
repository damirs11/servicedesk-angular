import { Entity } from '../entity/entity';
import { Folder } from '../folder/folder';

/**
 * Приоритет
 * @class
 * @name EntityPriority
 * @extends RESTEntity
 */
export class Template extends Entity {
  /**
   * Папка
   * @property
   * @name Template#folder
   * @type {Folder}
   */
  folder: Folder;
}
