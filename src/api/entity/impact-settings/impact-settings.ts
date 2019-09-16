import { Entity } from '../entity/entity';
import { EntityPriority } from '../entity-priority/entity-priority';
import { ServiceLevelPriority } from '../service-level-priority/service-level-priority';

/**
 * Условия предоставления. Определяет крайние сроки в заявках в зависимости от приоритета
 * @class
 * @name ImpactSettings
 */
export class ImpactSettings extends Entity {
  /**
   * Приоритет
   * @property
   * @name ImpactSettings#priority
   * @type {EntityPriority}
   */
  priority: EntityPriority;

  /**
   * Условия приоритета
   * @property
   * @name ImpactSettings#serviceLevelPriority
   * @type {ServiceLevelPriority}
   */
  serviceLevelPriority: ServiceLevelPriority;
}
