import { Entity } from "../entity/entity";

/**
 * База известных ошибок
 * @class
 * @name FAQ
 * @extends RESTEntity
 */
export class FAQ extends Entity {
  /**
   * Название
   * @property
   * @name FAQ#name
   * @type {string}
   */
  name: string;

  entityType: any;

  toString() {
    return this.name;
  }
}
