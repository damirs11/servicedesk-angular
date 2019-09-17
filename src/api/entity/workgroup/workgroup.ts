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
   * @property
   * @type {string}
   */
  status: string;

  /**
   * Отвественный группы
   * @property
   * @type {Person}
   */
  groupManager;

}
