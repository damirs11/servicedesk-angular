import { Entity } from "../entity/entity";

/**
 * Классификация
 * @class
 * @name EntityClassification
 * @extends RESTEntity
 */
export class EntityClassification extends Entity {
  /**
   * Название
   * @property
   * @name EntityClassification#name
   * @type {string}
   */
  name: string;

  entityType: any;

  toString() {
    return this.name;
  }
}
