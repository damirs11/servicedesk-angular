import { Entity } from '../entity/entity';

/**
 * Условие приоритета
 * @class
 * @name ServiceLevelPriority
 */
export class ServiceLevelPriorityDuration extends Entity {
  /**
   * Условия приоритета
   * @property
   * @name ServiceLevelPriorityDuration#serviceLevelPriority
   * @type {ServiceLevelPriority}
   */
  serviceLevelPriority;

  /**
   * Максимальный срок исполнения заявки
   * @property
   * @name ServiceLevelPriorityDuration#maximumDuration
   * @type {number}
   */
  maximumDuration: number;
}
