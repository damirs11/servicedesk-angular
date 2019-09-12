/**
 * Условия предоставления. Определяет крайние сроки в заявках в зависимости от приоритета
 * @class
 * @name ImpactSettings
 */
export class ImpactSettings {

    /**
     * Приоритет
     * @property
     * @name ImpactSettings#priority
     * @type {EntityPriority}
     */
    priority;

    /**
     * Условия приоритета
     * @property
     * @name ImpactSettings#serviceLevelPriority
     * @type {ServiceLevelPriority}
     */
    serviceLevelPriority;

}