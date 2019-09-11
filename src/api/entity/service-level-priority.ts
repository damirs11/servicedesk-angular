/**
 * Условие приоритета
 * @class
 * @name SD.ServiceLevelPriority
 */
export class ServiceLevelPriority {
    /**
     * Название
     * @property
     * @name SD.ServiceLevelPriority#name
     * @type {string}
     */
    name: string;

    /**
     * Порядок следования приоритета
     * @property
     * @name SD.ServiceLevelPriority#order
     * @type {number}
     */
    order: number;

    toString() {
        return String(this.name);
    }
}
