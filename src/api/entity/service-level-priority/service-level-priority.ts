import { Entity } from '../entity/entity';

/**
 * Условие приоритета
 * @class
 * @name ServiceLevelPriority
 */
export class ServiceLevelPriority extends Entity {
  /**
   * Порядок следования приоритета
   * @property
   * @name ServiceLevelPriority#order
   * @type {number}
   */
  order: number;
}
