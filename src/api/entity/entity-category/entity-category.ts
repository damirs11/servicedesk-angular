import { Entity } from "../entity/entity";

/**
 * Классификация
 * @class
 * @name EntityCategory
 * @extends RESTEntity
 */
export class EntityCategory extends Entity {
  /**
   * Название
   * @property
   * @name EntityCategory#name
   * @type {string}
   */
  name: string;

  entityType: any;

  toString() {
    return this.name;
  }
}
