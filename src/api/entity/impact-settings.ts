
/**
 * Условия предоставления. Определяет крайние сроки в заявках в зависимости от приоритета
 * @class
 * @name SD.ImpactSettings
 */
export class ImpactSettings {

    /**
     * Приоритет
     * @property
     * @name SD.ImpactSettings#priority
     * @type {SD.EntityPriority}
     */
    priority;

    /**
     * Условия приоритета
     * @property
     * @name SD.ImpactSettings#serviceLevelPriority
     * @type {SD.ServiceLevelPriority}
     */
    serviceLevelPriority;

}