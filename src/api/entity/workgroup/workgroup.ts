import { Entity } from '../entity/entity';

/**
 * Приоритет
 * @class
 * @name EntityPriority
 * @extends RESTEntity
 */
export class Workgroup extends Entity {
  /**
   * Название
   * @property
   * @name Workgroup#name
   * @type {string}
   */
  name: string;

  /**
   *
   * @property
   * @name Workgroup#searchcode
   * @type {string}
   */
  searchcode: string;

  /**
   * Статус
   * @property
   * @name Workgroup#status
   * @type {string}
   */
  status: string;

  /**
   * Отвественный группы
   * @property
   * @name ServiceCall#person
   * @type {Person}
   */
  groupManager;

  toString() {
    return this.name;
  }
}
