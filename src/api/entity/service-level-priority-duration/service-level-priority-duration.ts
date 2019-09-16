import { Entity } from '../entity/entity';
import { ServiceLevelPriority } from '../service-level-priority/service-level-priority';

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
  serviceLevelPriority: ServiceLevelPriority;

  /**
   * Максимальный срок исполнения заявки
   * @property
   * @name ServiceLevelPriorityDuration#maximumDuration
   * @type {number}
   */
  maximumDuration: number;
}
