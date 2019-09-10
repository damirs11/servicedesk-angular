import {Parse} from "./decorator/parse.decorator";

/**
 * Условие приоритета
 * @class
 * @name SD.ServiceLevelPriority
 */
export class ServiceLevelPriorityDuration {
    /**
     * Условия приоритета
     * @property
     * @name SD.ServiceLevelPriorityDuration#serviceLevelPriority
     * @type {SD.ServiceLevelPriority}
     */
    @Parse(data => SD.ServiceLevelPriority.parse(data)) serviceLevelPriority;

    /**
     * Максимальный срок исполнения заявки
     * @property
     * @name SD.ServiceLevelPriorityDuration#maximumDuration
     * @type {number}
     */
    @Parse(Number) maximumDuration: number;
}