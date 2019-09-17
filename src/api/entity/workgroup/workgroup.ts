import { Entity } from '../entity/entity';

/**
 * Приоритет
 */
export class Workgroup extends Entity {
  /**
   *
   */
  searchcode: string;

  /**
   * Статус
   */
  status: string;

  /**
   * Отвественный группы
   */
  groupManager;

}
