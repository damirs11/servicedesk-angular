import {Parse} from "./decorator/parse.decorator";

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
    @Parse(data => SD.EntityPriority.parse(data)) priority;

    /**
     * Условия приоритета
     * @property
     * @name SD.ImpactSettings#serviceLevelPriority
     * @type {SD.ServiceLevelPriority}
     */
    @Parse(data => SD.ServiceLevelPriority.parse(data)) serviceLevelPriority;

}