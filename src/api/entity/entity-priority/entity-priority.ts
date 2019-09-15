import { Entity } from "../entity/entity";

/**
 * Приоритет
 * @class
 * @name EntityPriority
 * @extends RESTEntity
 */
export class EntityPriority extends Entity {
  /**
   * Название
   * @property
   * @name EntityPriority#name
   * @type {string}
   */
  name: string;

  order: any;

  toString() {
    return this.name;
  }
}
