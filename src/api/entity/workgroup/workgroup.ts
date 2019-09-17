import { Entity } from '../entity/entity';

/**
 * Приоритет
 */
export class Workgroup extends Entity {
  /**
   *
   * @property
   * @type {string}
   */
  searchcode: string;

  /**
   * Статус
   * @type {string}
   */
  status: string;

  /**
   * Отвественный группы
   * @type {Person}
   */
  groupManager;

}
