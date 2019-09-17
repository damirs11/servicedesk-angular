import { Entity } from '../entity/entity';
import { ServiceLevelPriority } from '../service-level-priority/service-level-priority';

/**
 * Условие приоритета
 */
export class ServiceLevelPriorityDuration extends Entity {
  /**
   * Условия приоритета
   */
  serviceLevelPriority: ServiceLevelPriority;

  /**
   * Максимальный срок исполнения заявки
   */
  maximumDuration: number;
}
