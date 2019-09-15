import { Entity } from "../entity/entity";

/**
 * Статус
 * @class
 * @name EntityStatus
 * @extends RESTEntity
 */
export class EntityStatus extends Entity {
  /**
   * Название
   * @property
   * @name EntityStatus#name
   * @type {string}
   */
  name: string;

  order: string;

  toString() {
    return this.name;
  }
}
