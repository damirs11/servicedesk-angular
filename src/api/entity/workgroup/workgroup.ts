import { Entity } from '../entity/entity';

/**
 * Приоритет
 * @class
 * @name EntityPriority
 * @extends Entity
 */
export class Workgroup extends Entity {
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

}
