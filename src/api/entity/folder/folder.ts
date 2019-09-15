import { Entity } from '../entity/entity';

/**
 * Папка
 * @class
 * @name Folder
 * @extends RESTEntity
 */
export class Folder extends Entity {
  /**
   * Название
   * @property
   * @name Folder#name
   * @type {string}
   */
  name: string;

  entityType: any;

  toString() {
    return this.name;
  }
}
