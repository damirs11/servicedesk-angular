import { Entity } from "../entity/entity";

/**
 * Код завершения
 * @class
 * @name EntityClosureCode
 * @extends RESTEntity
 */
export class EntityClosureCode extends Entity {
  /**
   * Название
   * @property
   * @name EntityClosureCode#name
   * @type {string}
   */
  name: string;

  entityType;

  toString() {
    return this.name;
  }
}
